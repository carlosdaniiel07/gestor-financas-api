package com.carlos.gestorfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<Void> atualizaStatusMovimentos() {
		service.atualizaStatusMovimentos();
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/atualiza-status-cobrancas")
	public ResponseEntity<Void> atualizaStatusCobrancas() {
		service.atualizaStatusCobrancas();
		return ResponseEntity.ok(null);
	}
}
