package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.entities.Transferencia;
import com.carlos.gestorfinancas.services.TransferenciaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 17/07/2019
 */
@RestController
@RequestMapping(value = "/transferencias")
public class TransferenciasResource {
	@Autowired
	private TransferenciaService service;

	@GetMapping
	public ResponseEntity<List<Transferencia>> listar(@RequestParam(required = false, defaultValue = "0") int page) {
		return ResponseEntity.ok(service.getAll(page));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Transferencia> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<Transferencia> insere(@RequestBody Transferencia obj) {
		obj = service.insere(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}

	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Transferencia obj) {
		service.atualiza(obj);
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/efetiva/{id}")
	public ResponseEntity<Void> efetiva(@PathVariable Long id) {
		service.efetiva(id);
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/estorna/{id}")
	public ResponseEntity<Void> estorna(@PathVariable Long id) {
		service.estorna(id);
		return ResponseEntity.ok().body(null);
	}
}
