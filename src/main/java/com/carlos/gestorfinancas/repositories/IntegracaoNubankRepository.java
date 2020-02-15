package com.carlos.gestorfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.IntegracaoNubank;

@Repository
public interface IntegracaoNubankRepository extends JpaRepository<IntegracaoNubank, String> {
	
}
