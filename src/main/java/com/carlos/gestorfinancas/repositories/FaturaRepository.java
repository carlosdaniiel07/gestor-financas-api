package com.carlos.gestorfinancas.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {	
	List<Fatura> findByCartaoId(Long cartaoId);
	List<Fatura> findByCartaoIdAndReferencia(Long cartaoId, String referencia);
	List<Fatura> findByStatus(StatusFatura status);
	List<Fatura> findByVencimentoBetween(Date min, Date max);
	
	Optional<Fatura> findById(Long id);
	
	@Query(value = "SELECT TOP 1 * FROM fatura WHERE cartao_id = ?1 ORDER BY id DESC", nativeQuery = true)
	Optional<Fatura> findLastByCartaoId(Long cartaoId);
}