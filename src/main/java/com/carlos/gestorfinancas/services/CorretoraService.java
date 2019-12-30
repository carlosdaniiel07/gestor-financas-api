package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Corretora;
import com.carlos.gestorfinancas.repositories.CorretoraRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;

@Service
public class CorretoraService {

	@Autowired
	private CorretoraRepository repository;
	
	public List<Corretora> getAll(){
		return repository.findByAtivo(true);
	}
	
	public Corretora getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Não foi possível localizar esta corretora"));
	}
	
	public Corretora insere(Corretora corretora) {
		corretora.setRendimentoTotal(0);
		corretora.setValorAplicado(0);
		corretora.setAtivo(true);
		
		return repository.save(corretora);
	}
	
	public void atualiza(Corretora corretora) {
		corretora.setAtivo(true);
		repository.save(corretora);
	}
	
	public void remove(Corretora corretora) {
		if(corretora.isAtivo()) {
			corretora.setAtivo(false);
			repository.save(corretora);
		}
	}
	
	public void remove(Long corretoraId) {
		Corretora corretora = getById(corretoraId);
		
		if(corretora.isAtivo()) {
			corretora.setAtivo(false);
			repository.save(corretora);
		}
	}
	
	/**
	 * Incrementa o valor aplicado da corretora a partir de uma nova aplicação
	 * @param investimento
	 */
	public void novaAplicacao(Corretora corretora, double valor) {
		corretora.setValorAplicado(corretora.getValorAplicado() + valor);
		repository.save(corretora);
	}
	
	/**
	 * Diminui o valor aplicado da corretora a partir de um resgate
	 * @param investimento
	 */
	public void novoResgate(Corretora corretora, double valorAplicado, double rendimento) {
		corretora.setValorAplicado(corretora.getValorAplicado() - valorAplicado);
		corretora.setRendimentoTotal(corretora.getRendimentoTotal() + rendimento);
		repository.save(corretora);
	}
}
