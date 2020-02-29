package com.carlos.gestorfinancas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.IntegracaoTicket;

@Repository
public interface IntegracaoTicketRepository extends JpaRepository<IntegracaoTicket, String> {
	@Query(value = "SELECT t.id FROM IntegracaoTicket t")
	List<String> getAllTransactionId();
}
