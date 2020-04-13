package com.carlos.gestorfinancas.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.dtos.nubank.Categorizador;
import com.carlos.gestorfinancas.dtos.nubank.NubankAutenticacaoCPF;
import com.carlos.gestorfinancas.dtos.nubank.NubankAutenticacaoQRCode;
import com.carlos.gestorfinancas.dtos.nubank.NubankLoginResponseDTO;
import com.carlos.gestorfinancas.dtos.nubank.NubankQRCodeDTO;
import com.carlos.gestorfinancas.dtos.nubank.Transaction;
import com.carlos.gestorfinancas.entities.CartaoCredito;
import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.Subcategoria;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.services.exceptions.NubankServiceException;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.utils.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NubankService {
	
	@Autowired
	private IntegracaoNubankService integracaoNu;
	
	@Autowired
	private MovimentoService movimentoService;
	
	@Autowired
	private CartaoCreditoService cartaoCreditoService;
	
	@Autowired
	private FaturaService faturaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private SubcategoriaService subcategoriaService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	public final static String ORIGEM = "NUBANK";
	
	private final static String DISCOVER_LOGIN_URL = "https://prod-s0-webapp-proxy.nubank.com.br/api/discovery";
	private final static String DISCOVER_QR_CODE_LOGIN_URL = "https://prod-s0-webapp-proxy.nubank.com.br/api/app/discovery";
	
	private final String loginEndpoint;
	private final String loginQRCodeEndpoint;
	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	
	private List<NameValuePair> requestHeaders = new ArrayList<NameValuePair>();
	
	//private String eventsEndpoint;
	
	public NubankService() {
		this.httpClient = HttpClients.createDefault();
		this.objectMapper = new ObjectMapper();
		
		this.loginEndpoint = this.getLoginEndpoint();
		this.loginQRCodeEndpoint = this.getLoginQRCodeEndpoint();
		this.requestHeaders = this.getDefaultRequestHeaders();
	}
	
	/**
	 * Retorna um objeto NubankQRCodeDTO
	 * @return
	 */
	public NubankQRCodeDTO getNewQRCode() {
		return new NubankQRCodeDTO(this.generateUUID(), DateUtils.getDataAtual());
	}
	
	/**
	 * Realiza autenticação na API do Nubank
	 * @return
	 * @throws IOException 
	 */
	public NubankLoginResponseDTO auth(String qrCodeUUID) {
		String authResponse = null;
		JsonNode responseNode = null;
		String accessToken = null;
		
		try {
			// Realiza a 1ª autenticação na API do Nubank (utiliza somnete CPF + senha de acesso)
			NubankAutenticacaoCPF authCPF = new NubankAutenticacaoCPF("password", System.getenv("NUBANK_CPF"), System.getenv("NUBANK_SENHA"), "other.conta", "yQPeLzoHuJzlMMSAjC-LgNUJdUecx8XO");
			authResponse = this.makePostRequest(this.loginEndpoint, this.requestHeaders, this.objectMapper.writeValueAsString(authCPF));
			accessToken = this.objectMapper.readTree(authResponse).get("access_token").asText();
			
			// Atualiza token de acesso
			this.updateAccessToken(accessToken);
			
			// Realiza a 2ª autenticaçao na API do Nubank (utiliza o token gerado anteriomrnte + QR Code autorizado pelo aplicativo)
			NubankAutenticacaoQRCode authQRCode = new NubankAutenticacaoQRCode(qrCodeUUID, "login-webapp");
			authResponse = this.makePostRequest(this.loginQRCodeEndpoint, this.requestHeaders, this.objectMapper.writeValueAsString(authQRCode));
			responseNode = this.objectMapper.readTree(authResponse);
			accessToken = responseNode.get("access_token").asText();
			 
			// Atualiza token de acesso
			this.updateAccessToken(accessToken);
		
			// Grava um arquivo fisico no servidor com o JSON recebido como resposta
			this.logResponse(qrCodeUUID, this.objectMapper.writeValueAsString(responseNode));
			
			return new NubankLoginResponseDTO(accessToken);
		} catch (IOException e) {
			throw new NubankServiceException("Ocorreu um erro ao realizar autenticação com QR Code: " + e.getMessage());
		}
	}
	
	/**
	 * Realiza integração das transações do cartão de crédito
	 * @param qrCodeUUID
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<Transaction> integrarCartaoCredito(String qrCodeUUID) {
		List<Transaction> transacoesInseridas = new ArrayList<Transaction>();
		
		try {
			Collection<Transaction> transactions = this.getCreditCardTransactions(qrCodeUUID);
			Collection<Categorizador> categorizadores = this.readCategorizadorFile();
			Collection<String> transacoesImportadas = integracaoNu.getAllTransactionId();
			CartaoCredito cartaoCreditoNubank = cartaoCreditoService.getByNome("Nubank");
			Fatura ultimaFatura = faturaService.getLastByCartaoCredito(cartaoCreditoNubank.getId());
			
			if (ultimaFatura.getStatus() != StatusFatura.NAO_FECHADA) {
				throw new ObjetoNaoEncontradoException("Não foi encontrado nenhuma fatura em aberto para realizar a integração");
			}
			
			for (Transaction transaction : transactions) {
				if(!transacoesImportadas.contains(transaction.getId())) {
					// Insere movimento bancário
					movimentoService.insere(this.convertTransactionToMovimento(transaction, ultimaFatura, categorizadores));
					
					// Grava transação na tabela de integração
					integracaoNu.insert(transaction);
					
					transacoesInseridas.add(transaction);
				}
			}
			
			// Ajusta saldo da fatura, se necessário..
			if (!transacoesInseridas.isEmpty()) {
				faturaService.ajustaSaldo(ultimaFatura);
			}
			
			// Envia notificação referente a finalização da integração
			new Thread(() -> {
				notificacaoService.send("Integração Nubank", String.format("Integração finalizada com sucesso! %d movimento(s) importados!", transacoesInseridas.size()));
			}).start();
		} catch (IOException | ParseException | ObjetoNaoEncontradoException e) {
			throw new NubankServiceException("Ocorreu um erro ao realizar a integração do cartão de crédito: " + e.getMessage());
		}
		
		return transacoesInseridas;
	}
	
	/**
	 * Retorna as transações do cartão de crédito
	 * @param accessToken
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private Collection<Transaction> getCreditCardTransactions(String qrCodeUUID) throws IOException, ParseException {
		Collection<Transaction> transactions = new ArrayList<Transaction>();
		Transaction transaction;
		
		JsonNode jsonNode = this.objectMapper.readTree(this.readLog(qrCodeUUID));
		String endpoint = jsonNode.get("_links").get("events").get("href").asText();
		String accessToken = jsonNode.get("access_token").asText();
		
		// Atualiza token de acesso
		this.updateAccessToken(accessToken);
		
		// Recupera transações do cartão de crédito
		String response = this.makeGetRequest(endpoint, this.requestHeaders);
		Iterator<JsonNode> elementsIterator = this.objectMapper.readTree(response).get("events").elements();
		
		while (elementsIterator.hasNext()) {
			JsonNode node = elementsIterator.next();
			
			if (Transaction.isCreditCardTransaction(node.get("category").asText())) {
				transaction = new Transaction(
						node.get("id").asText(), 
						node.get("description").asText(), 
						node.get("category").asText(), 
						node.get("amount").asDouble() / 100, 
						node.get("amount_without_iof").asDouble() / 100, 
						new SimpleDateFormat("yyyy-MM-dd").parse(node.get("time").asText()), 
						node.get("title").asText());
				
				transactions.add(transaction);
			}
		}
		
		return transactions;
	}
	
	/**
	 * Converte uma transação do cartão de crédito para um movimento bancário
	 * @return
	 */
	private Movimento convertTransactionToMovimento(Transaction transaction, Fatura fatura, Collection<Categorizador> categorizadores) {
		Categoria categoria = null;
		Subcategoria subcategoria = null;
		
		Movimento movimento = new Movimento(
				null, 
				transaction.getDescription(), 
				'D', 
				DateUtils.getDataAtual(), 
				transaction.getTime(), 
				transaction.getAmount(), 
				0, 
				0, 
				StatusMovimento.EFETIVADO, 
				NubankService.ORIGEM, 
				"Movimento gerado via Integração Nubank", 
				null, 
				null,
				null,
				null, 
				fatura
		);
		
		// Categorização inteligente do movimento bancário
		for (Categorizador categorizador : categorizadores) {
			// Verifica inicialmente a propriedade 'title' do categorizador
			if (categorizador.getTitle() != null && categorizador.getTitle().equalsIgnoreCase(transaction.getTitle())) {
				categoria = categoriaService.getByNome(categorizador.getCategory());
				
				if (categorizador.getSucategory() != null) {
					subcategoria = subcategoriaService.getByNome(categorizador.getSucategory());
				}
				
				break;
			}
			
			// Verifica a coleção 'pattern' (padrões) do categorizador
			for (String pattern : categorizador.getPattern()) {
				if (transaction.getDescription().toLowerCase().contains(pattern.toLowerCase())) {
					categoria = categoriaService.getByNome(categorizador.getCategory());
					
					if (categorizador.getSucategory() != null) {
						subcategoria = subcategoriaService.getByNome(categorizador.getSucategory());
					}
					
					break;
				}
			}
			
			if (categoria != null || subcategoria != null) {
				break;
			}
		}
		
		movimento.setCategoria(categoria);
		movimento.setSubcategoria(subcategoria);
		
		return movimento; 
	}
	
	/**
	 * Realiza uma consulta na API e retorna o endpoint para login com CPF e senha
	 * @return
	 */
	private String getLoginEndpoint() {
		String loginUrl = null;
		String jsonResponse = this.makeGetRequest(DISCOVER_LOGIN_URL, null);
		
		try {
			loginUrl = this.objectMapper.readTree(jsonResponse).get("login").asText();
		} catch (IOException e) {
			throw new NubankServiceException(e.getMessage());
		}
		
		return loginUrl;
	}
	
	/**
	 * Realiza uma consulta na API e retorna o endpoint para login com QR Code
	 * @return
	 */
	private String getLoginQRCodeEndpoint() {
		String loginQRCodeUrl = null;
		String jsonResponse = this.makeGetRequest(DISCOVER_QR_CODE_LOGIN_URL, null);
		
		try {
			loginQRCodeUrl = this.objectMapper.readTree(jsonResponse).get("lift").asText();
		} catch (IOException e) {
			throw new NubankServiceException(e.getMessage());
		}
		
		return loginQRCodeUrl;
	}
		
	/**
	 * Gera um UUID (Identificador Único Universal)
	 * @return
	 */
	private String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Atualza a lista de headers com um novo token de acesso
	 * @param newAccessToken
	 */
	private void updateAccessToken(String newAccessToken) {
		boolean needToInsertKey = true;
		
		for (int i = 0; i < this.requestHeaders.size(); i++) {
			if (this.requestHeaders.get(i).getName().equalsIgnoreCase("Authorization")) {
				// Atualiza valor do header
				this.requestHeaders.set(i, new BasicNameValuePair("Authorization", "Bearer " + newAccessToken));
				needToInsertKey = false;
				break;
			}
		}
		
		if (needToInsertKey) {
			this.requestHeaders.add(new BasicNameValuePair("Authorization", "Bearer " + newAccessToken));
		}
	}
	
	/**
	 * Retorna o cabeçalho padrão utilizado em todas as requests
	 * @return
	 */
	private List<NameValuePair> getDefaultRequestHeaders() {
		List<NameValuePair> headers = new ArrayList<NameValuePair>();
		
		headers.add(new BasicNameValuePair("Content-Type", "application/json"));
		headers.add(new BasicNameValuePair("X-Correlation-Id", "WEB-APP.pewW9"));
		headers.add(new BasicNameValuePair("Origin", "https://conta.nubank.com.br"));
		headers.add(new BasicNameValuePair("Referer", "https://conta.nubank.com.br/"));
		headers.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36"));
		
		return headers;
	}
	
	/**
	 * Realiza uma requisição GET em um endpoint específico
	 * @param endpoint
	 * @return
	 */
	private String makeGetRequest(String endpoint, List<NameValuePair> headers) {
		String content = null;
		HttpGet request = new HttpGet(endpoint);
		
		if (headers != null) {
			headers.forEach((NameValuePair value) -> {
				request.addHeader(value.getName(), value.getValue());
			});
		}
		
		try {
			HttpResponse response = this.httpClient.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				content = EntityUtils.toString(response.getEntity());
			} else {
				throw new NubankServiceException("Falha ao consumir API do Nubank: " + EntityUtils.toString(response.getEntity()));
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			request.releaseConnection();
		}
		
		return content;
	}
	
	/**
	 * Realiza uma requisição POST em um endpoint específico
	 * @param endpoint
	 * @param requestBody
	 */
	private String makePostRequest(String endpoint, List<NameValuePair> headers, String requestBody) {
		String content = null;
		HttpPost request = new HttpPost(endpoint);
		
		if (headers != null) {
			headers.forEach((NameValuePair nameValuePair) -> {
				request.addHeader(nameValuePair.getName(), nameValuePair.getValue());
			});
		}
		
		try {
			request.setEntity(new StringEntity(requestBody));
			HttpResponse response = this.httpClient.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
				content = EntityUtils.toString(response.getEntity());
			} else {
				throw new NubankServiceException("Falha ao consumir API do Nubank: " + EntityUtils.toString(response.getEntity()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			request.releaseConnection();
		}
		
		return content;
	}
	
	/**
	 * Grava em um arquivo fisico (no formato JSON) todos os endpoints retornados na autenticação com QR Code
	 */
	private void logResponse(String qrCodeUUID, String json) {
		String pathName = "C:\\Nubank\\";
		String fileName = qrCodeUUID + ".json";
		File path = new File(pathName);
		
		if (!path.exists()) {
			if (!path.mkdir()) {
				throw new NubankServiceException("Ocorreu um erro ao criar o diretório " + pathName);
			}
		}
		
		// Inicia gravação do arquivo
		try {
			FileWriter fileWriter = new FileWriter(pathName + fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			// Grava JSON no arquivo
			bufferedWriter.write(json);
			
			bufferedWriter.flush();
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			throw new NubankServiceException("Ocorreu um erro ao gravar arquivo de log: " + e.getMessage());
		}
	}
	
	/**
	 * Lê um arquivo de log específico e retorna o seu conteúdo
	 * @param qrCodeUUI
	 * @return
	 */
	private String readLog(String qrCodeUUID) {
		StringBuilder stringBuilder = new StringBuilder();
		String filePathName = "C:\\Nubank\\" + qrCodeUUID + ".json";
		File file = new File(filePathName);
		
		if (file.exists()) {
			try {
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = bufferedReader.readLine();
				
				while (line != null) {
					stringBuilder.append(line);
					line = bufferedReader.readLine();
				}
				
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				throw new NubankServiceException("Ocorreu um erro ao realizar a leitura do arquivo de log: " + e.getMessage());
			}
		} else {
			throw new NubankServiceException("O arquivo de log específicado não existe (" + qrCodeUUID + ")");
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Lê o arquivo JSON que contém o mapeamento de categorias (utilizado p/ categorizar os movimentos/transações automaticamente)
	 * @return
	 */
	private Collection<Categorizador> readCategorizadorFile() {
		List<Categorizador> categorizadores = new ArrayList<Categorizador>();
		StringBuilder jsonFile = new StringBuilder();
		
		String filePathName = "C:\\Nubank\\categorizador.json";
		File file = new File(filePathName);
		
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {
				String line = reader.readLine();
				
				while (line != null) {
					jsonFile.append(line);
					line = reader.readLine();
				}
				
				categorizadores = this.objectMapper.readValue(jsonFile.toString(), new TypeReference<List<Categorizador>>(){});
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return categorizadores;
	}
}
