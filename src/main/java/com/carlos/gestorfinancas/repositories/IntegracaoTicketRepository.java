package com.carlos.gestorfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.IntegracaoTicket;

@Repository
public interface IntegracaoTicketRepository extends JpaRepository<IntegracaoTicket, String> {
	
}
