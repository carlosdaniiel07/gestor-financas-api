package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.CartaoCredito;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
	List<CartaoCredito> findByAtivo(boolean ativo);
	List<CartaoCredito> findByAtivo(boolean ativo, Pageable pagina);

	Optional<CartaoCredito> findByIdAndAtivo(Long id, boolean ativo);
}
