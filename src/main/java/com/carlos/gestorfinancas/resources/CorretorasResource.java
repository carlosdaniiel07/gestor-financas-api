package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.entities.Corretora;
import com.carlos.gestorfinancas.services.CorretoraService;

@RestController
@RequestMapping(value = "/corretoras")
public class CorretorasResource {

	@Autowired
	private CorretoraService service;
	
	@GetMapping
	public ResponseEntity<Collection<Corretora>> listar() {
		return ResponseEntity.ok(service.getAll());
	}

	@PostMapping
	public ResponseEntity<Corretora> insere(@RequestBody Corretora corretora) {
		Corretora obj = service.insere(corretora);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Corretora corretora) {
		service.atualiza(corretora);
		return ResponseEntity.ok().body(null);
	}
	
	@Deprecated
	@PutMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/{id}/remover")
	public ResponseEntity<Void> removeById(@PathVariable Long id) {
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}
}
