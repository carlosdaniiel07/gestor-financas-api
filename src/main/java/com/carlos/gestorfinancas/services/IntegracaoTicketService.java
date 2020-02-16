package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.IntegracaoTicket;
import com.carlos.gestorfinancas.repositories.IntegracaoTicketRepository;

@Service
public class IntegracaoTicketService {

	@Autowired
	private IntegracaoTicketRepository repository;
	
	public boolean exists(String idCode) {
		return repository.existsById(idCode);
	}
	
	public IntegracaoTicket insere(IntegracaoTicket transaction) {
		return repository.save(transaction);
	}
}
