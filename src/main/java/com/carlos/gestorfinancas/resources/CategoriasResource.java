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

import com.carlos.gestorfinancas.dtos.CategoriaDTO;
import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.services.CategoriaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@RestController
@RequestMapping(value = "/categorias")
public class CategoriasResource {
	@Autowired
	private CategoriaService service;

	@GetMapping
	public ResponseEntity<List<Categoria>> listar(@RequestParam(required = false, defaultValue = "0") int page) {
		return ResponseEntity.ok(service.getAll(page));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@PostMapping
	public ResponseEntity<Categoria> insere(@Valid @RequestBody CategoriaDTO objDTO) {
		Categoria categoria = service.insere(objDTO.toCategoria());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).body(categoria);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Categoria obj){
		service.atualiza(obj);
		
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		service.remove(id);
		
		return ResponseEntity.ok().body(null);
	}
}
