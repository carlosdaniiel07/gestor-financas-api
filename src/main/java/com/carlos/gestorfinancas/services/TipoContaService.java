package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.TipoConta;
import com.carlos.gestorfinancas.repositories.TipoContaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class TipoContaService {
	@Autowired
	private TipoContaRepository repository;

	public List<TipoConta> getAll() {
		return repository.findByAtivo(true);
	}
	
	public TipoConta getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Esse tipo de conta não foi encontrado."));
	}
	
	public TipoConta insere(TipoConta tipoConta) {
		if(repository.findByNomeAndAtivo(tipoConta.getNome(), true).isEmpty()) {
			tipoConta.setAtivo(true);
			return repository.save(tipoConta);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe um tipo de conta com o nome %s", tipoConta.getNome()));
		}
	}
	
	public void atualiza(TipoConta tipoConta) {
		List<TipoConta> tiposConta = repository.findByNomeAndAtivo(tipoConta.getNome(), true);
		tiposConta.remove(tipoConta);
		
		if(tiposConta.isEmpty()) {
			tipoConta.setAtivo(true);
			repository.save(tipoConta);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe um tipo de conta com o nome %s", tipoConta.getNome()));
		}
	}
	
	public void remove(Long id) {
		TipoConta obj = getById(id);
		
		if(obj.isAtivo()) {
			obj.setAtivo(false);
			repository.save(obj);
		}
	}
}
