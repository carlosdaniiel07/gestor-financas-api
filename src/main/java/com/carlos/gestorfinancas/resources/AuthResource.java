package com.carlos.gestorfinancas.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.dtos.AutenticacaoDTO;
import com.carlos.gestorfinancas.dtos.SuccessfulAutenticacaoDTO;
import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.services.AuthService;
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
	
	@Autowired
	private AuthService authService;
	
	
	@PostMapping
	public ResponseEntity<SuccessfulAutenticacaoDTO> login(@RequestBody AutenticacaoDTO authDTO) {
		return ResponseEntity.ok(authService.login(authDTO.getLoginOuEmail(), authDTO.getSenha()));
	}
	
	@PostMapping(value = "/refresh-token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		Usuario usuarioLogado = UsuarioService.getUsuarioLogado();
		String novoToken = jwtUtils.generateJwtToken(usuarioLogado.getLogin());
		
		response.addHeader("Authorization", "Bearer " + novoToken);
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/esqueci-minha-senha")
	public ResponseEntity<Void> esqueciMinhaSenha(@RequestBody AutenticacaoDTO objDto) {
		authService.recuperaSenha(objDto.getLoginOuEmail());
		
		return ResponseEntity.ok().body(null);
	}
}
