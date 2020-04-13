package com.carlos.gestorfinancas.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.dtos.ticket.TicketAutenticacaoDTO;
import com.carlos.gestorfinancas.dtos.ticket.Transaction;
import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.IntegracaoTicket;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.services.exceptions.TicketServiceException;
import com.carlos.gestorfinancas.utils.DateUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TicketService {
	
	@Autowired
	private IntegracaoTicketService integracaoService;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private MovimentoService movimentoService;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	public static String ORIGEM = "TICKE";
	
	// Dados para autenticação
	private final static String EMAIL = System.getenv("TICKET_EMAIL");
	private final static String SENHA = System.getenv("TICKET_SENHA");
	private final static int CARTAO_ID = Integer.parseInt(System.getenv("TICKET_CARTAO_ID"));
	
	private String accessToken;
	private String userId;
	
	private List<NameValuePair> headers = new ArrayList<NameValuePair>();
	
	private final ObjectMapper objectMapper;
	private final HttpClient httpClient;
	private final SimpleDateFormat dateFormat;
	
	private final String authUrl = "https://api.ticket.com.br/mobile/usuarios/v3/autenticar";
	private final String extratoUrl = "https://api.ticket.com.br/app/mobile/v1/usuarios/{userId}/cartoes/{cartaoId}/extrato";
	
	public TicketService() {
		this.objectMapper = new ObjectMapper();
		this.httpClient = HttpClients.createDefault();
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		this.headers = this.initHeaders();
	}
	
	/**
	 * Realiza integração/importação das transações do cartão refeição
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Collection<Transaction> IntegrarCartaoRefeicao() {
		List<Transaction> transacoesInseridas = new ArrayList<Transaction>();
		List<Transaction> transactions = this.getTransacoes();
		Collection<String> transactionsId = integracaoService.getAllTransactionId();
		
		Conta conta = contaService.getByNome("Ticket Restaurante");
		Categoria categoria = categoriaService.getAllByNome("Alimentação").get(0);
		
		Movimento movimento;
		IntegracaoTicket integracao;
		
		for (Transaction transaction : transactions) {
			if (!transactionsId.contains(transaction.getId())) {
				movimento = new Movimento(
						null, 
						transaction.isDebito() ? transaction.getDescription().substring(10) : transaction.getDescription(), // Ignora valor 'COMPRAS - ' que é colocado em todos os lançamentos do extrato
						transaction.getTipo(), 
						DateUtils.getDataAtual(), 
						transaction.getDateParsed(), 
						transaction.getValueParsed(), 
						0,
						0, 
						StatusMovimento.EFETIVADO, 
						TicketService.ORIGEM,
						"Movimento gerado via Integração Ticket", 
						conta, 
						categoria, 
						null,
						null, 
						null
				);
				
				integracao = new IntegracaoTicket(transaction.getId(), transaction.getDateParsed(), transaction.getValueParsed(), transaction.getDescription(), DateUtils.getDataAtual(), transaction.getTipo());
				
				// Insere movimento bancário
				movimentoService.insere(movimento);
				
				// Grava transação na tabela de integração
				integracaoService.insere(integracao);
				
				transacoesInseridas.add(transaction);
			}
		}
		
		// Envia notificação referente a finalização da integração
		new Thread(() -> {
			notificacaoService.send("Integração Ticket", String.format("Integração finalizada com sucesso! %d movimento(s) importados!", transacoesInseridas.size()));
		}).start();
		
		return transacoesInseridas;
	}
	
	/**
	 * Retorna todas as transações do cartão refeição
	 */
	private List<Transaction> getTransacoes() {
		List<Transaction> transacoes = new ArrayList<Transaction>();
		
		// Autenticação API
		this.auth();
		
		String newExtratoUrl = this.extratoUrl.replace("{userId}", this.userId).replace("{cartaoId}", String.valueOf(TicketService.CARTAO_ID)); 
		HttpGet request = new HttpGet(newExtratoUrl);
		
		// Atualiza cabeçalho da requisição com chave 'Authorization'
		headers.add(new BasicNameValuePair("Authorization", "Bearer " + this.accessToken));
		
		headers.forEach((NameValuePair value) -> {
			request.addHeader(value.getName(), value.getValue());
		});
		
		try {
			HttpResponse response = this.httpClient.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String jsonResponse = EntityUtils.toString(response.getEntity());
				Iterator<JsonNode> iterator = this.objectMapper.readTree(jsonResponse).get("value").get("release").elements();
				JsonNode currentNode;
				Transaction transaction;
				
				while(iterator.hasNext()) {
					currentNode = iterator.next();
					transaction = new Transaction(
							"", 
							this.dateFormat.parse(currentNode.get("dateParsed").asText()), 
							currentNode.get("valueParsed").asDouble(), 
							currentNode.get("description").asText(),
							currentNode.get("description").asText().contains("DISPONIB. DE CREDITO") ? 'C' : 'D'
					);
					
					transacoes.add(transaction);
				}
			} else {
				throw new TicketServiceException("Ocorreu uma falha ao consultar o extrato do cartão");
			}
		} catch (IOException | ParseException ex) {
			throw new TicketServiceException("Ocorreu uma falha ao consultar o extrato do cartão: " + ex.getMessage());
		}
		
		return transacoes;
	}
	
	/**
	 * Método responsável por realizar a auntenticação junto a API
	 */
	private void auth() {
		HttpPost request = new HttpPost(this.authUrl);
		TicketAutenticacaoDTO authObj = new TicketAutenticacaoDTO(TicketService.EMAIL, TicketService.SENHA);
		
		headers.forEach((NameValuePair value) -> {
			request.addHeader(value.getName(), value.getValue());
		});
		
		try {
			request.setEntity(new StringEntity(this.objectMapper.writeValueAsString(authObj)));
			HttpResponse response = this.httpClient.execute(request);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// Armazena 'access_token' e 'id_user'
				JsonNode jsonNode = this.objectMapper.readTree(EntityUtils.toString(response.getEntity()));
				
				this.accessToken = jsonNode.get("access_token").asText();
				this.userId = jsonNode.get("id_user").asText();
			} else {
				throw new TicketServiceException("Ocorreu um erro ao realizar a autenticação com a API");
			}
		} catch (IOException e) {
			throw new TicketServiceException("Ocorreu um erro ao realizar a autenticação com a API: " + e.getMessage());
		}
	}
	
	private List<NameValuePair> initHeaders() {
		List<NameValuePair> headers = new ArrayList<NameValuePair>();
		
		headers.add(new BasicNameValuePair("Content-Type", "application/json"));
		headers.add(new BasicNameValuePair("Host", "api.ticket.com.br"));
		headers.add(new BasicNameValuePair("Origin", "https://www.ticket.com.br"));
		
		return headers;
	}
}
