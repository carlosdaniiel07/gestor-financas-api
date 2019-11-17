package com.carlos.gestorfinancas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Transferencia;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
	
	@Query(value = "SELECT t FROM Transferencia t JOIN FETCH t.contaOrigem co JOIN FETCH co.tipo JOIN FETCH t.contaDestino cd JOIN FETCH cd.tipo")
	List<Transferencia> getAll();
}
