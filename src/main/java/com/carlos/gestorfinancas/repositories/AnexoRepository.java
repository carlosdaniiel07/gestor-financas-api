package com.carlos.gestorfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Anexo;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {
	
}
