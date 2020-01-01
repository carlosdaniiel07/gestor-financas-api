package com.carlos.gestorfinancas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Corretora;

@Repository
public interface CorretoraRepository extends JpaRepository<Corretora, Long> {
	List<Corretora> findByAtivo(boolean ativo);
	Optional<Corretora> findByIdAndAtivo(Long id, boolean ativo);
}
