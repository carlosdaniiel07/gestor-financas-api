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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.TipoContaDTO;
import com.carlos.gestorfinancas.entities.TipoConta;
import com.carlos.gestorfinancas.services.TipoContaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 15/07/2019
 */
@RestController
@RequestMapping(value = "/tipos-conta")
public class TiposContaResource {
	@Autowired
	private TipoContaService service;
	
	@GetMapping
	public ResponseEntity<List<TipoConta>> listar() {
		return ResponseEntity.ok().body(service.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TipoConta> buscar(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<TipoConta> insere(@Valid @RequestBody TipoContaDTO objDTO) {
		TipoConta obj = service.insere(objDTO.toTipoConta());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody TipoConta obj) {
		service.atualiza(obj);
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}
}

