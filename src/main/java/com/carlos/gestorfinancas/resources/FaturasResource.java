package com.carlos.gestorfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.dtos.FaturaPagamentoDTO;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.services.FaturaService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 05/08/2019
 */
@RestController
@RequestMapping(value =  "/faturas")
public class FaturasResource {
	@Autowired
	private FaturaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Fatura> buscar(@PathVariable Long id){
		return ResponseEntity.ok(service.getById(id));
	}
	
	@PutMapping(value = "/efetua-pagamento")
	public ResponseEntity<Void> paga(@RequestBody FaturaPagamentoDTO faturaDTO){
		service.efetuaPagamento(faturaDTO);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/abre/{id}")
	public ResponseEntity<Void> abre(@PathVariable Long id){
		service.abre(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/fecha/{id}")
	public ResponseEntity<Void> fecha(@PathVariable Long id){
		service.fecha(id);
		return ResponseEntity.ok(null);
	}
}
