package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Conta;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
	List<Conta> findByAtivo(boolean ativo);
	
	@Query("SELECT c FROM Conta c JOIN FETCH c.tipo WHERE c.ativo = ?1")
	List<Conta> findByAtivo(boolean ativo, Pageable pagina);
	
	Optional<Conta> findByIdAndAtivo(Long id, boolean ativo);
	
	List<Conta> findByNomeAndAtivo(String nome, boolean ativo);
}
