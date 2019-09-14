package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.carlos.gestorfinancas.dtos.ContaDTO;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.services.ContaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 15/07/2019
 */
@RestController
@RequestMapping(value = "/contas")
public class ContasResource {
	@Autowired
	private ContaService service;

	@GetMapping
	public ResponseEntity<List<Conta>> listar(@RequestParam(required = false, defaultValue = "0") int page) {
		return ResponseEntity.ok(service.getAll(page));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Conta> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}

	@GetMapping(value = "/{id}/movimentos")
	public ResponseEntity<List<Movimento>> listarMovimentos(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id).getMovimentos());
	}
	
	@PostMapping
	public ResponseEntity<Conta> insere(@Valid @RequestBody ContaDTO objDTO) {
		Conta obj = service.insere(objDTO.toConta());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Conta obj) {
		service.atualiza(obj);
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}
}
