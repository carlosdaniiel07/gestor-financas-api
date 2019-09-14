package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Fatura;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {	
	List<Fatura> findByCartaoId(Long cartaoId);
	
	List<Fatura> findByCartaoIdAndReferencia(Long cartaoId, String referencia);
	
	Optional<Fatura> findById(Long id);
}