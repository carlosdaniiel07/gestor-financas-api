package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.CartaoCredito;
import com.carlos.gestorfinancas.repositories.CartaoCreditoRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class CartaoCreditoService {
	@Autowired
	private CartaoCreditoRepository repository;

	private final int dadosPorPagina = 30;
	
	List<CartaoCredito> getAll() {
		return repository.findByAtivo(true);
	}
	
	List<CartaoCredito> getAll(int pagina) {
		return repository.findByAtivo(true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public CartaoCredito getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Este cartão de crédito não foi encontrado."));
	}
	
	public CartaoCredito insere(CartaoCredito cartaoCredito) {
		return repository.save(cartaoCredito);
	}
	
	public void atualiza(CartaoCredito cartaoCredito) {
		cartaoCredito.setAtivo(true);
		repository.save(cartaoCredito);
	}
	
	public void remove(Long id) {
		CartaoCredito obj = getById(id);
		
		if(obj.isAtivo()) {
			obj.setAtivo(false);
			repository.save(obj);
		}
	}
}
