package com.carlos.gestorfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.services.TaskService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 05/10/2019
 */
@RestController
@RequestMapping(value = "/tasks")
public class TasksResource {
	
	@Autowired
	private TaskService service;
	
	@PostMapping(value = "/atualiza-status-movimentos")
	public ResponseEntity<Void> atualizaStatusMovimentos(@RequestParam(required = false) String authorizationCode) {
		service.atualizaStatusMovimentos(authorizationCode);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/atualiza-status-cobrancas")
	public ResponseEntity<Void> atualizaStatusCobrancas(@RequestParam(required = false) String authorizationCode) {
		service.atualizaStatusCobrancas(authorizationCode);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/alerta-cobrancas-vencer")
	public ResponseEntity<Void> alertaCobrancasVencer(@RequestParam(required = false) String authorizationCode) {
		service.alertaCobrancasVencer(authorizationCode);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/fecha-fatura-cartao")
	public ResponseEntity<Void> fechaFaturaCartao(@RequestParam(required = false) String authorizationCode) {
		service.fechaFaturaCartao(authorizationCode);
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/grava-saldo-diario")
	public ResponseEntity<Void> gravaSaldoDiario(@RequestParam(required = false) String authorizationCode) {
		service.gravaSaldoDiario(authorizationCode);
		return ResponseEntity.ok(null);
	}
}
