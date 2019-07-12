package com.carlos.gestorfinancas.services;

import java.util.ArrayList;
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
	
	public void atualiza(Subcategoria subcategoria) {
		if(subcategoria.isEditavel()) {
			if(repository.findByNomeAndAtivo(subcategoria.getNome(), true).size() <= 1) {
				subcategoria.setAtivo(true);
				
				repository.save(subcategoria);
			} else {
				throw new OperacaoInvalidaException(String.format("Já existe uma subcategoria com o nome %s", subcategoria.getNome()));
			}
		} else {
			throw new OperacaoInvalidaException("Esta subcategoria foi gerada automaticamente e não pode ser alterada.");
		}
	}
	
	public void remove(Long id) {
		Subcategoria obj = getById(id);
		
		if(obj.isEditavel()) {
			if(obj.isAtivo()) {
				obj.setAtivo(false);
				repository.save(obj);
			}
		} else {
			throw new OperacaoInvalidaException("Esta subcategoria foi gerada automaticamente e não pode ser excluída.");
		}
	}
	
	public void remove(Subcategoria subcategoria) {
		if(subcategoria.isEditavel()) {
			if(subcategoria.isAtivo()) {
				subcategoria.setAtivo(true);
				repository.save(subcategoria);
			}
		} else {
			throw new OperacaoInvalidaException("Esta subcategoria foi gerada automaticamente e não pode ser excluída.");
		}
	}
	
	public void remove(List<Subcategoria> subcategorias) {
		List<Subcategoria> subcategoriasParaExcluir = new ArrayList<Subcategoria>();
		
		subcategorias.forEach((subcategoria) -> {
			if(subcategoria.isAtivo() && subcategoria.isEditavel()) {
				subcategoriasParaExcluir.add(subcategoria);
			}
		});
		
		repository.saveAll(subcategoriasParaExcluir);
	}
}
