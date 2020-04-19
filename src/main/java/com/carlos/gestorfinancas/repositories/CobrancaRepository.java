package com.carlos.gestorfinancas.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.enums.StatusCobranca;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
	List<Cobranca> findByDataVencimento(Date dataVencimento);
	
	List<Cobranca> findByStatus(StatusCobranca status);
	List<Cobranca> findByStatusIn(Collection<StatusCobranca> status);
	
	@Query(value = "SELECT c FROM Cobranca c JOIN FETCH c.beneficiario")
	List<Cobranca> getAll(Pageable page);
	
	List<Cobranca> findByDataVencimentoBetween(Date min, Date max);
}
