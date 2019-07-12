package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Categoria;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	List<Categoria> findByAtivo(boolean ativo);
	List<Categoria> findByAtivo(boolean ativo, Pageable paginacao);
	
	Optional<Categoria> findByIdAndAtivo(Long id, boolean ativo);
	
	List<Categoria> findByNomeAndAtivo(String nome, boolean ativo);
	List<Categoria> findByNomeAndAtivo(String nome, boolean ativo, Pageable paginacao);
	
	List<Categoria> findByTipoAndAtivo(char tipo, boolean ativo);
	
	List<Categoria> findByNomeAndTipoAndAtivo(String nome, char tipo, boolean ativo);
}
