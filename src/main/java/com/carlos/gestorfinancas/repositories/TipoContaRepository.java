package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.TipoConta;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface TipoContaRepository extends JpaRepository<TipoConta, Long> {
	List<TipoConta> findByAtivo(boolean ativo);
	
	List<TipoConta> findByNomeAndAtivo(String nome, boolean ativo);
	
	Optional<TipoConta> findByIdAndAtivo(Long id, boolean ativo);
}
