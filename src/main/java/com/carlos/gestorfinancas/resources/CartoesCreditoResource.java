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

import com.carlos.gestorfinancas.dtos.CartaoCreditoDTO;
import com.carlos.gestorfinancas.entities.CartaoCredito;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.services.CartaoCreditoService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 15/07/2019
 */
@RestController
@RequestMapping(value = "/cartoes-credito")
public class CartoesCreditoResource {
	@Autowired
	private CartaoCreditoService service;

	@GetMapping
	public ResponseEntity<List<CartaoCredito>> listar(@RequestParam(required = false, defaultValue = "0") int page) {
		return ResponseEntity.ok(service.getAll(page));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CartaoCredito> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping(value = "/{id}/faturas")
	public ResponseEntity<List<Fatura>> listarFaturas(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id).getFaturas());
	}
	
	@PostMapping
	public ResponseEntity<CartaoCredito> insere(@Valid @RequestBody CartaoCreditoDTO objDTO) {
		CartaoCredito obj = service.insere(objDTO.toCartaoCredito());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody CartaoCredito obj) {
		service.atualiza(obj);
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
