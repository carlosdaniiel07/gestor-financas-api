package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.AplicacaoResgateDTO;
import com.carlos.gestorfinancas.dtos.InvestimentoDTO;
import com.carlos.gestorfinancas.entities.Investimento;
import com.carlos.gestorfinancas.entities.enums.TipoItemInvestimento;
import com.carlos.gestorfinancas.services.InvestimentoService;

@RestController
@RequestMapping(value = "/investimentos")
public class InvestimentosResource {

	@Autowired
	private InvestimentoService service;
	
	@GetMapping
	public ResponseEntity<Collection<Investimento>> listar(){
		return ResponseEntity.ok(service.getAll());
	}
	
	@PostMapping
	public ResponseEntity<Investimento> insere(@Valid @RequestBody InvestimentoDTO objDTO) {
		Investimento obj = service.insere(objDTO.toInvestimento());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PostMapping(value = "/add-item")
	public ResponseEntity<Investimento> addAplicacao(@Valid @RequestBody AplicacaoResgateDTO objDTO) {
		Investimento obj = null;
		
		if (objDTO.getItem().getTipo() == TipoItemInvestimento.REINVESTIMENTO) {
			obj = service.addReinvestimento(objDTO.getInvestimento(), objDTO.getItem());
		} else {
			obj = service.addResgate(objDTO.getInvestimento(), objDTO.getItem());
		}
		
		return ResponseEntity.created(null).body(obj);
	}
	
	@PutMapping
	public ResponseEntity<Void> atualiza(@RequestBody Investimento investimento) {
		service.atualiza(investimento);
		return ResponseEntity.ok(null); 
	}
}
