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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.CobrancaDTO;
import com.carlos.gestorfinancas.dtos.CobrancaPagamentoDTO;
import com.carlos.gestorfinancas.dtos.CobrancaRemocaoDTO;
import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.OperacaoCobranca;
import com.carlos.gestorfinancas.services.CobrancaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 15/07/2019
 */
@RestController
@RequestMapping(value = "/cobrancas")
public class CobrancasResource {
	@Autowired
	private CobrancaService service;

	@GetMapping
	public ResponseEntity<List<Cobranca>> listar(@RequestParam(required = false, defaultValue = "0") int page) {
		return ResponseEntity.ok(service.getAll(page));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cobranca> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping(value = "/{id}/operacoes")
	public ResponseEntity<List<OperacaoCobranca>> listarOperacoes(@PathVariable Long id) {
		return ResponseEntity.ok(service.getById(id).getOperacoes());
	}
	
	@PostMapping
	public ResponseEntity<Cobranca> insere(@Valid @RequestBody CobrancaDTO objDTO) {
		Cobranca obj = service.insere(objDTO.toCobranca());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}

	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Cobranca obj) {
		service.atualiza(obj);
		return ResponseEntity.ok().body(null);
	}
	
	@PutMapping(value = "/efetua-pagamento")
	public ResponseEntity<Void> paga(@RequestBody CobrancaPagamentoDTO obj) {
		service.efetuaPagamento(obj);
		return ResponseEntity.ok().body(null);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> remove(@RequestBody CobrancaRemocaoDTO obj) {
		service.remove(obj);
		return ResponseEntity.ok().body(null);
	}
}
