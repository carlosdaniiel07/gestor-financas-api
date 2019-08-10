package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.repositories.UsuarioRepository;
import com.carlos.gestorfinancas.services.exceptions.AuthenticationException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@Service
public class UsuarioService implements UserDetailsService {
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	public Usuario insere(Usuario usuario) {
		usuario.setSenha(bCrypt.encode(usuario.getSenha()));
		return repository.save(usuario);
	}

	@Override
	public UserDetails loadUserByUsername(String loginOuEmail) throws UsernameNotFoundException {
		return repository.findByLoginOrEmailAndAtivo(loginOuEmail, true)
				.orElseThrow(() -> new UsernameNotFoundException("Não foi possível localizar um usuário com este login ou com este endereço de e-mail"));
	}
	
	/**
	 * Retorna o usuário logado
	 * @return
	 */
	public static Usuario getUsuarioLogado() {
		try {
			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (ClassCastException ex) {
			throw new AuthenticationException("Não há nenhum usuário logado.");
		}
	}
}
