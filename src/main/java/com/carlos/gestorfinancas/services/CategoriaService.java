package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Subcategoria;
import com.carlos.gestorfinancas.repositories.CategoriaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/07/2019
 */
@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repository;
	
	@Autowired
	private SubcategoriaService subcategoriaService;

	private final int dadosPorPagina = 30;
	
	public List<Categoria> getAll() {
		return repository.findByAtivo(true, null);
	}
	
	public List<Categoria> getAll(int pagina ) {
		return repository.findByAtivo(true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public List<Categoria> getAllByNome(String nome) {
		return repository.findByNomeAndAtivo(nome, true);
	}
	
	public List<Categoria> getAllByNome(String nome, int pagina) {
		return repository.findByNomeAndAtivo(nome, true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public List<Categoria> getAllByTipo(char tipo) {
		return repository.findByTipoAndAtivo(tipo, true);
	}

	public Categoria getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Essa categoria não foi encontrada."));
	}
	
	public Categoria insere (Categoria categoria ) {
		categoria.setEditavel(true);
		categoria.setAtivo(true);
		
		return repository.save(categoria);
	}
	
	public void atualiza(Categoria categoria) {
		if(categoria.isEditavel()) {
			List<Categoria> categorias = repository.findByNomeAndTipoAndAtivo(categoria.getNome(), categoria.getTipo(), true);
			categorias.remove(categoria);
			
			if(categorias.isEmpty()) {
				categoria.setAtivo(true);
				
				repository.save(categoria);
			} else {
				throw new OperacaoInvalidaException(String.format("Já existe uma categoria com o nome %s", categoria.getNome()));
			}
		} else {
			throw new OperacaoInvalidaException("Esta categoria foi gerada automaticamente e não pode ser alterada.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Long id) {
		Categoria obj = getById(id);
		List<Subcategoria> subcategorias = obj.getSubcategorias();
		
		if(obj.isAtivo()) {
			if(obj.isEditavel()) {
				obj.setAtivo(false);
				subcategoriaService.remove(subcategorias);
				
				repository.save(obj);
			} else {
				throw new OperacaoInvalidaException("Esta categoria foi gerada automaticamente e não pode ser excluída.");
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Categoria categoria) {
		if(categoria.isAtivo()) {
			if(categoria.isEditavel()) {
				List<Subcategoria> subcategorias = categoria.getSubcategorias();
				
				categoria.setAtivo(false);
				subcategoriaService.remove(subcategorias);
				
				repository.save(categoria);
			} else {
				throw new OperacaoInvalidaException("Esta categoria foi gerada automaticamente e não pode ser excluída.");
			}
		}
	}
}
