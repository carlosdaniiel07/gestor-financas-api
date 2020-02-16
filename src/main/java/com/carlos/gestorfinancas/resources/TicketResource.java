package com.carlos.gestorfinancas.resources;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.dtos.ticket.Transaction;
import com.carlos.gestorfinancas.services.TicketService;

@RestController
@RequestMapping(value = "/ticket")
public class TicketResource {

	@Autowired
	private TicketService service;
	
	@PostMapping(value = "/integrar-cartao-refeicao")
	public ResponseEntity<Collection<Transaction>> integrarCartaoRefeicao() {
		return ResponseEntity.ok(service.IntegrarCartaoRefeicao());
	}
}
