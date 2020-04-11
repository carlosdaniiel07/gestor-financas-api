package com.carlos.gestorfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.entities.Notificacao;
import com.carlos.gestorfinancas.services.NotificacaoService;

@RestController
@RequestMapping(value = "/notificacoes")
public class NotificacoesResource {
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Notificacao> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(notificacaoService.getById(id));
	}
	
	@PutMapping(value = "/{id}/recebido")
	public ResponseEntity<Void> marcarComoRecebido(@PathVariable Long id) {
		notificacaoService.markAsReceived(id);
		return ResponseEntity.ok(null);
	}
}
