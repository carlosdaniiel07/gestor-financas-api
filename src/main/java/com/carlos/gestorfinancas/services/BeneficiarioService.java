package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Beneficiario;
import com.carlos.gestorfinancas.repositories.BeneficiarioRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Service
public class BeneficiarioService {
	@Autowired
	private BeneficiarioRepository repository;

	private final int dadosPorPagina = 30;
	
	public List<Beneficiario> getAll() {
		return repository.findByAtivo(true);
	}
	
	public List<Beneficiario> getAll(int pagina) {
		return repository.findByAtivo(true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public Beneficiario getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Esse beneficiário não foi encontrado."));
	}
	
	public Beneficiario insere(Beneficiario beneficiario) {
		if(repository.findByNomeAndAtivo(beneficiario.getNome(), true).isEmpty()) {
			beneficiario.setAtivo(true);
			return repository.save(beneficiario);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe um beneficiário cadastrado com o nome %s", beneficiario.getNome()));
		}
	}
	
	public void atualiza(Beneficiario beneficiario) {
		List<Beneficiario> beneficiarios = repository.findByNomeAndAtivo(beneficiario.getNome(), true);
		beneficiarios.remove(beneficiario);
		
		if(beneficiarios.isEmpty()) {
			beneficiario.setAtivo(true);
			repository.save(beneficiario);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe um beneficiário cadastrado com o nome %s", beneficiario.getNome()));
		}
	}
	
	public void remove(Long id) {
		Beneficiario obj = getById(id);
		
		if(obj.isAtivo()) {
			obj.setAtivo(false);
			repository.save(obj);
		}
	}
}
