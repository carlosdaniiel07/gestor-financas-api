package com.carlos.gestorfinancas.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
	
	/*
	 * Gera um token assinado com JWT (Json Web Token)
	 */
	public String generateJwtToken(String login) {
		return Jwts.builder().setSubject(login)
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes()).compact();
	}
}
