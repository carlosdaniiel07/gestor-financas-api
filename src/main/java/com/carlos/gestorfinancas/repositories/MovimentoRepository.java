package com.carlos.gestorfinancas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Movimento;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface MovimentoRepository extends JpaRepository<Movimento, Long> {
	List<Movimento> findByContaId(Long contaId);
}
