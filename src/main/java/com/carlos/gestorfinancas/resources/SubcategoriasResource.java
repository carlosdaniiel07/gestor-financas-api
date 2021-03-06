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

import com.carlos.gestorfinancas.dtos.SubcategoriaDTO;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.Subcategoria;
import com.carlos.gestorfinancas.services.SubcategoriaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@RestController
@RequestMapping(value = "/subcategorias")
public class SubcategoriasResource {
	@Autowired
	private SubcategoriaService service;

	@GetMapping
	public ResponseEntity<List<Subcategoria>> listar() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Subcategoria> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping(value = "/{id}/movimentos")
	public ResponseEntity<List<Movimento>> listarMovimentos(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id).getMovimentos());
	}
	
	@PostMapping
	public ResponseEntity<Subcategoria> insere(@Valid @RequestBody SubcategoriaDTO objDTO) {
		Subcategoria obj = service.insere(objDTO.toSubcategoria());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Subcategoria obj){
		service.atualiza(obj);
		
		return ResponseEntity.ok().body(null);
	}
	
	@Deprecated
	@PutMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}

	@PutMapping(value = "/{id}/remover")
	public ResponseEntity<Void> removeById(@PathVariable Long id){
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}
}
