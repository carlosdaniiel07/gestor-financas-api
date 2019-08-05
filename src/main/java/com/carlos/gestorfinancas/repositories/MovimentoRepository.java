package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Movimento;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
	List<Movimento> findByContaId(Long contaId);

	@Query(value = "SELECT "
			+ "			SUM((valor + acrescimo - decrescimo)) "
			+ "		FROM movimento "
			+ "		WHERE (conta_id = ?1) AND (tipo = 'C') AND (status = 'EFETIVADO')", nativeQuery = true)
	Optional<Double> getTotalCreditoByConta(Long contaId);
	
	@Query(value = "SELECT "
			+ "			SUM((valor + acrescimo - decrescimo)) "
			+ "		FROM movimento "
			+ "		WHERE (conta_id = ?1) AND (tipo = 'D') AND (status = 'EFETIVADO') AND (fatura_id IS NULL)", nativeQuery = true)
	Optional<Double> getTotalDebitoByConta(Long contaId);
}
