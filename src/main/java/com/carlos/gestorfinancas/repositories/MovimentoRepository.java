package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
	
	@Query(value ="SELECT m FROM Movimento m 				"
			+ "	   		LEFT JOIN FETCH m.conta	c			"
			+ "	   		LEFT JOIN FETCH c.tipo				"
			+ "			LEFT JOIN FETCH m.categoria			"
			+ "			LEFT JOIN FETCH m.subcategoria s	"
			+ "			LEFT JOIN FETCH s.categoria			"
			+ "			LEFT JOIN FETCH m.projeto			"
			+ "			LEFT JOIN FETCH m.fatura f			"
			+ "			LEFT JOIN FETCH f.cartao			"
			+ "ORDER BY m.dataContabilizacao DESC"
	)
	List<Movimento> getAll(Pageable page);
	
	@Query(value ="SELECT m FROM Movimento m 				"
			+ "	   		LEFT JOIN FETCH m.conta	c			"
			+ "	   		LEFT JOIN FETCH c.tipo				"
			+ "			LEFT JOIN FETCH m.categoria			"
			+ "			LEFT JOIN FETCH m.subcategoria s	"
			+ "			LEFT JOIN FETCH s.categoria			"
			+ "			LEFT JOIN FETCH m.projeto			"
			+ "			LEFT JOIN FETCH m.fatura f			"
			+ "			LEFT JOIN FETCH f.cartao			"
			+ "	   WHERE m.conta.id = ?1"
	)
	List<Movimento> findByContaId(Long contaId);
	
	@Query(value ="SELECT m FROM Movimento m 				"
			+ "	   		LEFT JOIN FETCH m.conta	c			"
			+ "	   		LEFT JOIN FETCH c.tipo				"
			+ "			LEFT JOIN FETCH m.categoria			"
			+ "			LEFT JOIN FETCH m.subcategoria s	"
			+ "			LEFT JOIN FETCH s.categoria			"
			+ "			LEFT JOIN FETCH m.projeto			"
			+ "			LEFT JOIN FETCH m.fatura f			"
			+ "			LEFT JOIN FETCH f.cartao			"
			+ "	   WHERE m.conta.id = ?1"
	)
	List<Movimento> findByContaId(Long contaId, Pageable page);
	
	List<Movimento> findByStatus(StatusMovimento status);

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
	
	@Query(value = "SELECT																															"
					+ "	(																															"
					+ "		ISNULL((SELECT SUM((valor + acrescimo - decrescimo)) FROM movimento WHERE (fatura_id = ?1) AND (tipo = 'D')), 0) -		"
					+ "		ISNULL((SELECT SUM((valor + acrescimo - decrescimo)) FROM movimento WHERE (fatura_id = ?1) AND (tipo = 'C')), 0)		"
					+ "	)																															" 
					, nativeQuery = true)
	Optional<Double> getTotalDebitoByFatura(Long faturaId);
}
