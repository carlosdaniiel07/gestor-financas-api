package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.OperacaoCobranca;
import com.carlos.gestorfinancas.repositories.OperacaoCobrancaRepository;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Service
public class OperacaoCobrancaService {
	@Autowired
	private OperacaoCobrancaRepository repository;
	
	public OperacaoCobranca insere(OperacaoCobranca operacao) {
		return repository.save(operacao);
	}
}
