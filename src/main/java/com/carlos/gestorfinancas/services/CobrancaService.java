package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusCobranca;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.repositories.CobrancaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Repository
public class CobrancaService {
	@Autowired
	private CobrancaRepository repository;
	
	private final int dadosPorPagina = 30;
	private final String modulo = "COBRC";
	
	public List<Cobranca> getAll() {
		return repository.findAll();
	}
	
	public List<Cobranca> getAll(int pagina) {
		return repository.findAll(PageRequest.of(pagina, dadosPorPagina)).getContent();
	}
	
	public Cobranca getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Essa cobrança não foi encontrada."));
	}
	
	public Cobranca insere(Cobranca cobranca) {
		if(cobranca.getValorTotal() <= cobranca.getBeneficiario().getLimite()) {
			cobranca.setDataPagamento(null);
			cobranca.setStatus(StatusCobranca.PENDENTE);
			cobranca.setSaldo(cobranca.getValorTotal());
			cobranca.setDataAgendamento(cobranca.getDataVencimento());
		
			return repository.save(cobranca);
		} else {
			throw new OperacaoInvalidaException(String.format("O valor da cobrança é superior ao limite deste beneficiário (%f)", cobranca.getValorTotal()));
		}		
	}
}
