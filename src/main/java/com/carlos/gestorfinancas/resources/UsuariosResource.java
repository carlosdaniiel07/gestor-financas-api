package com.carlos.gestorfinancas.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.UsuarioDTO;
import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.services.UsuarioService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@RestController
@RequestMapping(value = "/usuarios")
public class UsuariosResource {
	@Autowired
	private UsuarioService service;

	@PostMapping
	public ResponseEntity<Usuario> insere(@RequestBody UsuarioDTO objDTO) {
		Usuario obj = service.insere(objDTO.toUsuario());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
}
