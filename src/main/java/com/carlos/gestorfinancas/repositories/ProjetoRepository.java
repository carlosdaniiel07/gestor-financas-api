package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Projeto;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/07/2019
 */
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
	Optional<Projeto> findByIdAndAtivo(Long id, boolean ativo);
	
	List<Projeto> findByAtivo(boolean ativo);
	List<Projeto> findByAtivo(boolean ativo, Pageable paginacao);
}
