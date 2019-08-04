package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Subcategoria;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
	List<Subcategoria> findByAtivo(boolean ativo);
	List<Subcategoria> findByAtivo(boolean ativo, Pageable paginacao);
	
	Optional<Subcategoria> findByIdAndAtivo(Long id, boolean ativo);
	
	List<Subcategoria> findByNomeAndCategoriaIdAndAtivo(String nome, Long categoriaId, boolean ativo);
	List<Subcategoria> findByNomeAndAtivo(String nome, boolean ativo);
	List<Subcategoria> findByNomeAndAtivo(String nome, boolean ativo, Pageable paginacao);
}
