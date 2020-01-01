package com.carlos.gestorfinancas.resources;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.entities.ModalidadeInvestimento;
import com.carlos.gestorfinancas.services.ModalidadeInvestimentoService;

@RestController
@RequestMapping(value = "/modalidades-investimento")
public class ModalidadesInvestimentoResource {

	@Autowired
	private ModalidadeInvestimentoService service;
	
	@GetMapping
	public ResponseEntity<Collection<ModalidadeInvestimento>> listar() {
		return ResponseEntity.ok(service.getAll());
	}
}
