package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.repositories.UsuarioRepository;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	public Usuario insere(Usuario usuario) {
		usuario.setSenha(bCrypt.encode(usuario.getSenha()));
		
		
		return null;
	}
}
