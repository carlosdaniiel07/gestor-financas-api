package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.dtos.SuccessfulAutenticacaoDTO;
import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.utils.JWTUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
@Service
public class AuthService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	public void recuperaSenha(String loginOuEmail) {
		usuarioService.recuperaSenha(loginOuEmail);
	}
	
	public SuccessfulAutenticacaoDTO login(String loginOuEmail, String senha) {
		Usuario user = (Usuario)usuarioService.loadUserByUsername(loginOuEmail);
		
		if (bCrypt.matches(senha, user.getSenha())) {
			return new SuccessfulAutenticacaoDTO(user, "Bearer " + jwtUtils.generateJwtToken(user.getLogin()));
		} else {
			throw new ObjetoNaoEncontradoException("Não foi possível encontrar um usuário com estas credenciais");
		}
	}
}
