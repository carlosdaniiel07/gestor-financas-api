package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.MovimentoDTO;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.services.MovimentoService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
@RestController
@RequestMapping(value = "/movimentos")
public class MovimentoResource {

	@Autowired
	private MovimentoService service;
	
	@GetMapping
	public ResponseEntity<List<Movimento>> listar(@RequestParam(name = "page", defaultValue = "0", required = false) int page ) {
		return ResponseEntity.ok(service.getAll(page));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Movimento> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<Movimento> insere(@Valid @RequestBody MovimentoDTO objDTO) {
		Movimento obj = service.insere(objDTO.toMovimento());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
}
