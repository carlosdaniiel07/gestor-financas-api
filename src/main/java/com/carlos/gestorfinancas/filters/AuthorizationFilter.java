package com.carlos.gestorfinancas.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.carlos.gestorfinancas.utils.JWTUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 09/08/2019
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtils jwtUtils;
	private UserDetailsService userDetailsService;
	
	/**
	 * @param authenticationManager
	 */
	public AuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, UserDetailsService userDetailsService) {
		super(authenticationManager);
		
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String authorizationHeader = request.getHeader("Authorization");
		String token = (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) ? authorizationHeader.substring(7) : null;
		
		if(jwtUtils.isValid(token)) {
			String login = jwtUtils.getLoginByToken(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(login);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			
			// Caso seja um usuário válido..
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		// Realiza o filtro na requisição
		chain.doFilter(request, response);
	}
}
