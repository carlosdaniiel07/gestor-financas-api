package com.carlos.gestorfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carlos.gestorfinancas.entities.SaldoDiario;

public interface SaldoDiarioRepository extends JpaRepository<SaldoDiario, Long> {
	
}
