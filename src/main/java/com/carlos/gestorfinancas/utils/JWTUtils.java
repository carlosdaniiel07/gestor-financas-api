package com.carlos.gestorfinancas.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 09/08/2019
 */
@Component
public class JWTUtils {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expiration}")
	private Long jwtExpiration;
	
	/**
	 * Gera um token assinado com JWT (Json Web Token)
	 */
	public String generateJwtToken(String login) {
		return Jwts.builder().setSubject(login)
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes()).compact();
	}
	
	/**
	 * Verifica se um dado token é válido (se baseia na assinatura/secret e na data de expiração)
	 */
	public boolean isValid(String token) {
		if(token != null) {
			Claims claims = getClaimsByToken(token);
			
			if(claims != null) {
				String login = claims.getSubject();
				Date expirationDate = claims.getExpiration();
				
				return (login != null && expirationDate != null && new Date().before(expirationDate));
			}
		} 
		
		return false;
	}
	
	/**
	 * Retorna o login do usuário baseado em um token
	 */
	public String getLoginByToken(String token) {
		Claims claims = getClaimsByToken(token);
		
		return (claims != null) ? claims.getSubject() : null;
	}
	
	/**
	 * Retorna as 'reivindicações' (dados) de um dado token
	 * @param token
	 * @return
	 */
	private Claims getClaimsByToken(String token) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
		} catch (MalformedJwtException | SignatureException | ExpiredJwtException ex) {
			return null;
		}
	}
}
