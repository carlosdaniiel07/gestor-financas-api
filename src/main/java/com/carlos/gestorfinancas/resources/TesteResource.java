package com.carlos.gestorfinancas.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.services.S3Service;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
@RestController
@RequestMapping(value = "/teste")
public class TesteResource {
	
	@Autowired
	private S3Service s3Service;
	
	@PostMapping
	public ResponseEntity<Void> uploadFile(@RequestParam MultipartFile file) {
		s3Service.uploadFile(file);
		
		return ResponseEntity.ok().body(null);
	}
}
