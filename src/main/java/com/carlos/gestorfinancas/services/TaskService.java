package com.carlos.gestorfinancas.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.LogTask;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.SaldoDiario;
import com.carlos.gestorfinancas.entities.enums.StatusCobranca;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.repositories.LogTaskRepository;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 05/10/2019
 */
@Service
public class TaskService {

	@Autowired
	private LogTaskRepository repository;
	
	@Autowired
	private MovimentoService movimentoService;
	
	@Autowired
	private CobrancaService cobrancaService;
	
	@Autowired
	private FaturaService faturaService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private SaldoDiarioService saldoDiarioService;
	
	@Value("${tasks.authorization-code}")
	private String authorizationCode;
	
	@Value("${param.intervalo-aviso-vencimento}")
	private String intervaloAvisoVencimento;
	
	/**
	 * Atualiza o status dos movimentos agendados. Todos os movimentos agendados (status = StatusMovimento.AGENDADO) 
	 * com data de contabilização inferior ou igual a data atual terão o seu status atualizado para EFETIVADO (status = StatusMovimento.EFETIVADO)
	 */
	public void atualizaStatusMovimentos(String authorizationCode) {
		checkAuthorizationCode(authorizationCode);
		
		Date dataAtual = DateUtils.getDataAtual();
		List<Movimento> movimentosAgendados = movimentoService.getAllByStatus(StatusMovimento.AGENDADO);
		List<Movimento> movimentosParaAtualizar = new ArrayList<Movimento>();
		
		movimentosAgendados.forEach((Movimento movimento) -> {
			Date dataContabilizacao = movimento.getDataContabilizacao();
			
			// Caso a data de contabilização seja inferior ou igual a data atual
			if(dataContabilizacao.compareTo(dataAtual) == 0 || dataContabilizacao.compareTo(dataAtual) < 0) {
				movimento.setStatus(StatusMovimento.EFETIVADO);
				movimentosParaAtualizar.add(movimento);
			}
		});
		
		movimentoService.atualiza(movimentosParaAtualizar, true);
		
		this.gravaLogExecucao("atualizaStatusMovimentos");
	}
	
	/**
	 * Atualiza o status das cobranças agendadas. Todas as cobranças agendadas (status = StatusCobranca.AGENDADO) com data de pagamento
	 * inferior ou igual a data atual terão o seu status atualizado para PAGO ou PAGO_PARCIAL. Nota: a atualização do status de uma cobrança
	 * não implica na geração/alteração de um movimento bancário
	 */
	public void atualizaStatusCobrancas(String authorizationCode) {
		checkAuthorizationCode(authorizationCode);
		
		Date dataAtual = DateUtils.getDataAtual();
		List<Cobranca> cobrancasAgendadas = cobrancaService.getAllByStatus(StatusCobranca.AGENDADO);

		cobrancasAgendadas.forEach((Cobranca cobranca) -> {
			Date dataPagamento = cobranca.getDataPagamento();
			
			// Caso a data de pagamento seja inferior ou igual a data atual
			if(dataPagamento.compareTo(dataAtual) <= 0) {
				cobrancaService.atualizaParaPago(cobranca);
			}
		});
		
		this.gravaLogExecucao("atualizaStatusCobrancas");
	}
	
	/**
	 * Envia um alerta por e-mail com todas as cobranças a vencer (status = StatusCobranca.PENDENTE)
	 */
	public void alertaCobrancasVencer(String authorizationCode) {
		checkAuthorizationCode(authorizationCode);
	
		List<Cobranca> cobrancasVencer = cobrancaService.getAllByStatus(StatusCobranca.PENDENTE);
		List<Cobranca> cobrancasAlerta = new ArrayList<Cobranca>();
		Set<Integer> dias = new HashSet<Integer>();
		
		for (String valor : intervaloAvisoVencimento.split("-")) {
			dias.add(Integer.parseInt(valor));
		}
		
		cobrancasVencer.forEach((Cobranca cobranca) -> {
			long diff = Math.abs(cobranca.getDataVencimento().getTime() - DateUtils.getDataAtual().getTime());
			int diferencaDias = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
			// A cobrança será enviada por e-mail somente se a diferença de dias entre vencimento e data atual estiver dentro dos parâmetros
			if(dias.contains(diferencaDias)) {
				cobrancasAlerta.add(cobranca);
			}
		});
		
		// Envia o e-mail de alerta
		if(!cobrancasAlerta.isEmpty()) {
			emailService.enviaEmail("Cobranças próximas ao vencimento", emailService.getEmailsFromParam(), "alertaCobrancaVencer", 
					  "cobrancas", cobrancasAlerta);
		}
		
		this.gravaLogExecucao("alertaCobrancasVencer");
	}
	
	/**
	 * Envia um alerta por e-mail com todas as faturas de cartão de crédito a vencer
	 */
	public void alertaFaturasVencer(String authorizationCode) {
		checkAuthorizationCode(authorizationCode);
	
		List<Fatura> faturasAlerta = new ArrayList<Fatura>();
		Set<Integer> dias = new HashSet<Integer>();
		
		for (String valor : intervaloAvisoVencimento.split("-")) {
			dias.add(Integer.parseInt(valor));
		}
		
		faturaService.getAllByStatus(StatusFatura.PENDENTE).forEach((Fatura fatura) -> {
			long diff = Math.abs(fatura.getVencimento().getTime() - DateUtils.getDataAtual().getTime());
			int diferencaDias = (int)TimeUnit.DAYS.toDays(diff);
			
			if (dias.contains(diferencaDias)) {
				faturasAlerta.add(fatura);
			}
		});
		
		if (!faturasAlerta.isEmpty()) {
			emailService.enviaEmail("Faturas próximas do vencimento", emailService.getEmailsFromParam(), "", "faturas", faturasAlerta);
		}
		
		this.gravaLogExecucao("alertaFaturasVencer");
	}
	
	/**
	 * Fecha as faturas dos cartões de crédito
	 */
	public void fechaFaturaCartao(String authorizationCode) {
		checkAuthorizationCode(authorizationCode);
	
		List<Fatura> faturasEmAberto = faturaService.getAllByStatus(StatusFatura.NAO_FECHADA);
		List<Fatura> faturasAlerta = new ArrayList<Fatura>();
		int diaDoMes = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		faturasEmAberto.forEach((Fatura fatura) -> {
			// Caso a data de fechamento da fatura seja hoje..
			if(fatura.getCartao().getDiaFechamento() == diaDoMes) {
				faturaService.fecha(fatura);
				faturasAlerta.add(fatura);
			}
		});
		
		// Envia uma notificação por e-mail
		if(!faturasAlerta.isEmpty()) {
			emailService.enviaEmail("Fechamento fatura cartão de crédito", emailService.getEmailsFromParam(), "alertaFechamentoFatura", 
					  "faturas", faturasAlerta);
		}
		
		this.gravaLogExecucao("fechaFaturaCartao");
	}
	
	/**
	 * Grava o saldo diário de cada conta cadastrada na tabela 'saldo_diario'
	 * @param authorizationCode
	 */
	public void gravaSaldoDiario(String authorizationCode) {
		checkAuthorizationCode(authorizationCode);
		
		List<Conta> contas = contaService.getAll();
		List<SaldoDiario> colecaoSaldoDiario = new ArrayList<SaldoDiario>();
		
		for(Conta conta : contas) {
			colecaoSaldoDiario.add(new SaldoDiario(null, conta.getBanco(), conta.getNome(), conta.getSaldo(), new Date()));
		}
		
		saldoDiarioService.insere(colecaoSaldoDiario);
		
		this.gravaLogExecucao("gravaSaldoDiario");
	}
	
	/**
	 * Retorna todo o histórico de tarefas automatizadas (jobs) executadas
	 * @return
	 */
	public Collection<LogTask> getAll() {		
		return repository.findAll();
	}
	
	/**
	 * Verifica se o código de autorização recebido é válido (compara com a variável de ambiente TASKS_AUTHORIZATION_CODE)
	 * @param value
	 * @return
	 */
	private void checkAuthorizationCode(String value) {
		if(value == null || !value.equals(authorizationCode)) {
			throw new OperacaoInvalidaException("O código de autorização fornecido é inválido.");
		}
	}
	
	/**
	 * Grava a execução da tarefa automatizada no banco de dados
	 */
	private void gravaLogExecucao(String nome) {
		Date dataAtual = DateUtils.getDataAtual();
		String obs = "Tarefa executada com sucesso em " + DateUtils.getDataAtualAsBrFormat();
		String origem = "Azure";
		
		LogTask obj = new LogTask(null, nome, origem, dataAtual, obs, true);
		
		repository.save(obj);
	}
	
	/**
	 * Executa uma tarefa a partir do seu nome (exige que o usuário já esteja autenticado)
	 * @param taskName
	 */
	public void executa(String taskName) {
		if (UsuarioService.getUsuarioLogado() != null) {
			switch (taskName) {
			case "alertaCobrancasVencer":
				this.alertaCobrancasVencer(authorizationCode);
				break;
			case "alertaFaturasVencer":
				this.alertaFaturasVencer(authorizationCode);
				break;
			case "atualizaStatusCobrancas":
				this.atualizaStatusCobrancas(authorizationCode);
				break;
			case "atualizaStatusMovimentos":
				this.atualizaStatusMovimentos(authorizationCode);
				break;
			case "fechaFaturaCartao":
				this.fechaFaturaCartao(authorizationCode);
				break;
			case "gravaSaldoDiario":
				this.gravaSaldoDiario(authorizationCode);
				break;
			default:
				throw new OperacaoInvalidaException("Nenhuma task localizada a partir do nome " + taskName);
			}
		}
	}
}
