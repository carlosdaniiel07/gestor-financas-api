package com.carlos.gestorfinancas.services;

import java.util.Arrays;
import java.util.Random;

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
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@Service
public class UsuarioService implements UserDetailsService {
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder bCrypt;

	public Usuario insere(Usuario usuario) {
		usuario.setSenha(bCrypt.encode(usuario.getSenha()));
		return repository.save(usuario);
	}

	/**
	 * Retorna um usuário através do seu login ou através do seu e-mail
	 */
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
	
	/**
	 * Rotina para a recuperação de senha de um usuário
	 * @param loginOuEmail
	 */
	public void recuperaSenha(String loginOuEmail) {
		Usuario usuario = (Usuario)loadUserByUsername(loginOuEmail);
		
		if(usuario.isAtivo()) {
			// Gera uma nova senha e atualiza o perfil do usuário
			String novaSenha = geraSenha();
			usuario.setSenha(bCrypt.encode(novaSenha));
			repository.save(usuario);
			
			// Envia e-mail com a nova senha
			emailService.enviaEmail("Processo de recuperação de senha", Arrays.asList(usuario.getEmail()), "recuperacaoSenha", "usuario", 
					new Usuario(usuario.getId(), usuario.getNome(), usuario.getLogin(), novaSenha, usuario.getEmail(), usuario.isAtivo(), usuario.getTipo()));
		} else {
			throw new OperacaoInvalidaException("O seu usuário foi removido ou está bloqueado.");
		}
	}
	
	/**
	 * Gera uma nova senha
	 * @return
	 */
	private String geraSenha() {
		Random randomNumber = new Random();
		String novaSenha = "";
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		int qtdChars = randomNumber.nextInt(11) + 5;
		
		for(int i = 0; i < qtdChars; i++) {
			novaSenha += chars[randomNumber.nextInt(chars.length + 1)];
		}
		
		return novaSenha;
	}
}
