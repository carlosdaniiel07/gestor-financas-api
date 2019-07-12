package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Subcategoria;
import com.carlos.gestorfinancas.repositories.SubcategoriaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Service
public class SubcategoriaService {
	@Autowired
	private SubcategoriaRepository repository;
	
	private final int dadosPorPagina = 30;
	
	public List<Subcategoria> getAll() {
		return repository.findByAtivo(true);
	}
	
	public List<Subcategoria> getAll(int pagina ) {
		return repository.findByAtivo(true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public List<Subcategoria> getAllByNome(String nome) {
		return repository.findByNomeAndAtivo(nome, true);
	}
	
	public List<Subcategoria> getAllByNome(String nome, int pagina) {
		return repository.findByNomeAndAtivo(nome, true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public Subcategoria getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Essa subcategoria não foi encontrada."));
	}
	
	public Subcategoria insere(Subcategoria subcategoria) {
		if(repository.findByNomeAndAtivo(subcategoria.getNome(), true).isEmpty()) {
			subcategoria.setEditavel(true);
			subcategoria.setAtivo(true);
			
			return repository.save(subcategoria);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe uma subcategoria com o nome %s", subcategoria.getNome()));
		}
	}
	
	public void atualizar(Subcategoria subcategoria) {
		if(repository.findByNomeAndAtivo(subcategoria.getNome(), true).size() <= 1) {
			subcategoria.setEditavel(true);
			subcategoria.setAtivo(true);
			
			repository.save(subcategoria);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe uma subcategoria com o nome %s", subcategoria.getNome()));
		}
	}
	
	public void remover(Long id) {
		Subcategoria obj = getById(id);
		
		obj.setAtivo(false);
		repository.save(obj);
	}
	
	public void remover(Subcategoria subcategoria) {
		if(subcategoria.isAtivo()) {
			subcategoria.setAtivo(true);
			repository.save(subcategoria);
		}
	}
	
	public void remover(List<Subcategoria> subcategorias) {
		subcategorias.forEach((subcategoria) -> subcategoria.setAtivo(false));
		repository.saveAll(subcategorias);
	}
}
