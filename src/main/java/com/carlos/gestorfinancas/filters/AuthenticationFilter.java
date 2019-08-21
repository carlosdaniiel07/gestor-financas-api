package com.carlos.gestorfinancas.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.carlos.gestorfinancas.dtos.AutenticacaoDTO;
import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 09/08/2019
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	private JWTUtils jwtUtils;
	
	/**
	 * 
	 */
	public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}
	
	// Irá interceptar todas as requisições para o endpoint /login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Authentication auth = null;
		
		try {
			AutenticacaoDTO autenticacaoDto = new ObjectMapper().readValue(request.getInputStream(), AutenticacaoDTO.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(autenticacaoDto.getLoginOuEmail(), autenticacaoDto.getSenha(), new ArrayList<>());
			
			// Verifica se as credenciais são válidas a partir da implementação feita na classe UsuarioService
			auth = authenticationManager.authenticate(authToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return auth;
	}
	
	// Ocorre quando a autenticação for realizada com sucesso
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		Usuario usuario = ((Usuario) authResult.getPrincipal());
		String jwtToken = jwtUtils.generateJwtToken(usuario.getLogin());
		PrintWriter writer = response.getWriter();
		String json = new ObjectMapper().writeValueAsString(usuario);
		
		writer.print(json);
		
		response.setContentType("application/json");
		response.addHeader("Authorization", "Bearer " + jwtToken);
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Origin", "*");	
	
		writer.close();
		writer.flush();
	}
}
