package com.carlos.gestorfinancas.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/status")
public class StatusResource {

	@GetMapping(value = "/is-alive")
	public ResponseEntity<Void> isAlive()
	{
		return ResponseEntity.ok(null);
	}
}
