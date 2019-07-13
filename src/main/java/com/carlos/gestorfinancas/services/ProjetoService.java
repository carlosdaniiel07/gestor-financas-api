package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Projeto;
import com.carlos.gestorfinancas.entities.enums.StatusProjeto;
import com.carlos.gestorfinancas.repositories.ProjetoRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/07/2019
 */
@Service
public class ProjetoService {
	@Autowired
	private ProjetoRepository repository;
	
	private final int dadosPorPagina = 30;
	
	public List<Projeto> getAll() {
		return repository.findByAtivo(true);
	}
	
	public List<Projeto> getAll(int pagina) {
		return repository.findByAtivo(true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public Projeto getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Este projeto não foi encontrado."));
	}
	
	public Projeto insere(Projeto projeto) {
		projeto.setDataInicial(DateUtils.getCurrentDate());
		projeto.setAtivo(true);
		projeto.setStatus(StatusProjeto.EM_ANDAMENTO);
		
		if(projeto.getDataFinal() != null && projeto.getDataFinal().before(projeto.getDataInicial())) {
			throw new OperacaoInvalidaException("A data final do projeto não pode ser anterior a data inicial.");
		} else {
			return repository.save(projeto);	
		}
	}
	
	public void atualiza(Projeto projeto) {
		projeto.setAtivo(true);
		repository.save(projeto);
	}
	
	public void remove(Long id) {
		Projeto obj = getById(id);
		
		if(obj.isAtivo()) {
			obj.setAtivo(false);
			repository.save(obj);
		}
	}
	
	public void remove(Projeto projeto) {
		if(projeto.isAtivo()) {
			projeto.setAtivo(false);
			repository.save(projeto);
		}
	}
}
