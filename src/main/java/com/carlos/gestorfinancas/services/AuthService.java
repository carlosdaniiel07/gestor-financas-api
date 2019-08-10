package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
@Service
public class AuthService {
	@Autowired
	private UsuarioService usuarioService;
	
	public void recuperaSenha(String loginOuEmail) {
		usuarioService.recuperaSenha(loginOuEmail);
	}
}
