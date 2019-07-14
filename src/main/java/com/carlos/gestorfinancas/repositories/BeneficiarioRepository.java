package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Beneficiario;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
	List<Beneficiario> findByAtivo(boolean ativo);
	List<Beneficiario> findByAtivo(boolean ativo, Pageable pagina);
	
	Optional<Beneficiario> findByIdAndAtivo(Long id, boolean ativo);
	
	List<Beneficiario> findByNomeAndAtivo(String nome, boolean ativo);
}
