package com.carlos.gestorfinancas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.IntegracaoNubank;

@Repository
public interface IntegracaoNubankRepository extends JpaRepository<IntegracaoNubank, String> {
	
	@Query(value = "SELECT t.transactionId FROM IntegracaoNubank t")
	List<String> getAllTransactionId();
}
