package com.carlos.gestorfinancas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.OperacaoCobranca;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Repository
public interface OperacaoCobrancaRepository extends JpaRepository<OperacaoCobranca, Long> {
	List<OperacaoCobranca> findByCobrancaId(Long cobrancaId);
}
