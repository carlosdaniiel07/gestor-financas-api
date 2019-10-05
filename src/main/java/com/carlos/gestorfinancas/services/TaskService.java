package com.carlos.gestorfinancas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusCobranca;
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
}
