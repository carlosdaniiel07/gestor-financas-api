package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.repositories.FaturaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class FaturaService {
	@Autowired
	private FaturaRepository repository;

	List<Fatura> getAll() {
		return repository.findAll();
	}
	
	public List<Fatura> getByCartaoCredito(Long cartaoCreditoId) {
		return repository.findByCartaoId(cartaoCreditoId);
	}
	
	public Fatura getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Esta fatura não foi encontrada."));
	}
	
	public Fatura insere(Fatura fatura) {
		fatura.setDataPagamento(null);
		fatura.setValorPago(0);
		fatura.setStatus(StatusFatura.NAO_FECHADA);
		
		return repository.save(fatura);
	}
}
