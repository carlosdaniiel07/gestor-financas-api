package com.carlos.gestorfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.entities.Anexo;
import com.carlos.gestorfinancas.services.AnexoService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
@RestController
@RequestMapping(value = "/teste")
public class TesteResource {
	
	@Autowired
	private AnexoService anexoService;
	
	@PostMapping
	public ResponseEntity<Anexo> uploadFile(@RequestParam MultipartFile file) {
		Anexo obj = anexoService.insere(file);
		
		return ResponseEntity.created(null).body(obj);
	}
}
