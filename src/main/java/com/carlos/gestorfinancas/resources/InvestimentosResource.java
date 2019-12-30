package com.carlos.gestorfinancas.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.AplicacaoResgateDTO;
import com.carlos.gestorfinancas.dtos.InvestimentoDTO;
import com.carlos.gestorfinancas.entities.Investimento;
import com.carlos.gestorfinancas.services.InvestimentoService;

@RestController
@RequestMapping(value = "/investimentos")
public class InvestimentosResource {

	@Autowired
	private InvestimentoService service;
	
	@PostMapping
	public ResponseEntity<Investimento> insere(@Valid @RequestBody InvestimentoDTO objDTO) {
		Investimento obj = service.insere(objDTO.toInvestimento());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PostMapping(value = "/aplicacao")
	public ResponseEntity<Investimento> addAplicacao(@Valid @RequestBody AplicacaoResgateDTO objDTO) {
		Investimento obj = service.addReinvestimento(objDTO.getInvestimento(), objDTO.getItem());
		return ResponseEntity.created(null).body(obj);
	}
	
	@PostMapping(value = "/resgate")
	public ResponseEntity<Investimento> addResgate(@Valid @RequestBody AplicacaoResgateDTO objDTO) {
		Investimento obj = service.addResgate(objDTO.getInvestimento(), objDTO.getItem());
		return ResponseEntity.created(null).body(obj);
	}
}
