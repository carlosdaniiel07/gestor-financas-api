package com.carlos.gestorfinancas.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.services.UsuarioService;
import com.carlos.gestorfinancas.utils.JWTUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PostMapping(value = "/refresh-token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		Usuario usuarioLogado = UsuarioService.getUsuarioLogado();
		String novoToken = jwtUtils.generateJwtToken(usuarioLogado.getLogin());
		
		response.addHeader("Authorization", "Bearer " + novoToken);
		
		return ResponseEntity.noContent().build();
	}
}
