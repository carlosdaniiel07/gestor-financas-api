package com.carlos.gestorfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Transferencia;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
	
}
