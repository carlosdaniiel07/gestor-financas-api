package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.MovimentoDTO;
import com.carlos.gestorfinancas.entities.Anexo;
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
	
	@PostMapping(value = "/{id}/anexos")
	public ResponseEntity<Anexo> insereAnexo (@PathVariable Long id, @RequestParam MultipartFile file) {
		return ResponseEntity.created(null).body(service.insereAnexo(id, file));
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Movimento obj) {
		service.atualiza(obj);
		return ResponseEntity.ok().body(null);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		service.remove(id);
		return ResponseEntity.ok().body(null);
	}
}
