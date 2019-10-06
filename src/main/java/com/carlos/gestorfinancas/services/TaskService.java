package com.carlos.gestorfinancas.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusCobranca;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 05/10/2019
 */
@Service
public class TaskService {

	@Autowired
	private MovimentoService movimentoService;
	
	@Autowired
	private CobrancaService cobrancaService;
	
	@Autowired
	private FaturaService faturaService;
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * Atualiza o status dos movimentos agendados. Todos os movimentos agendados (status = StatusMovimento.AGENDADO) 
	 * com data de contabilização inferior ou igual a data atual terão o seu status atualizado para EFETIVADO (status = StatusMovimento.EFETIVADO)
	 */
	public void atualizaStatusMovimentos() {
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
		
		movimentoService.atualiza(movimentosParaAtualizar);
	}
	
	/**
	 * Atualiza o status das cobranças agendadas. Todas as cobranças agendadas (status = StatusCobranca.AGENDADO) com data de pagamento
	 * inferior ou igual a data atual terão o seu status atualizado para PAGO ou PAGO_PARCIAL. Nota: a atualização do status de uma cobrança
	 * não implica na geração/alteração de um movimento bancário
	 */
	public void atualizaStatusCobrancas() {
		Date dataAtual = DateUtils.getDataAtual();
		List<Cobranca> cobrancasAgendadas = cobrancaService.getAllByStatus(StatusCobranca.AGENDADO);

		cobrancasAgendadas.forEach((Cobranca cobranca) -> {
			Date dataPagamento = cobranca.getDataPagamento();
			
			// Caso a data de pagamento seja inferior ou igual a data atual
			if(dataPagamento.compareTo(dataAtual) <= 0) {
				cobrancaService.atualizaParaPago(cobranca);
			}
		});
	}
	
	/**
	 * Envia um alerta por e-mail com todas as cobranças a vencer (status = StatusCobranca.PENDENTE)
	 */
	public void alertaCobrancasVencer() {
		List<Cobranca> cobrancasVencer = cobrancaService.getAllByStatus(StatusCobranca.PENDENTE);
		List<Cobranca> cobrancasAlerta = new ArrayList<Cobranca>();
		Set<Integer> dias = new HashSet<Integer>();
		
		dias.addAll(Arrays.asList(0, 1, 5, 10, 15));
		
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
			emailService.enviaEmail("Cobranças próximas ao vencimento", UsuarioService.getUsuarioLogado().getEmail(), "alertaCobrancaVencer", 
					  "cobrancas", cobrancasAlerta);
		}
	}
	
	/**
	 * Fecha as faturas dos cartões de crédito
	 */
	public void fechaFaturaCartao() {
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
			emailService.enviaEmail("Fechamento fatura cartão de crédito", UsuarioService.getUsuarioLogado().getEmail(), "alertaFechamentoFatura", 
					  "faturas", faturasAlerta);
		}
	}
}
