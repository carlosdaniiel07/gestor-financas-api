package com.carlos.gestorfinancas.services.exceptions;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthenticationException(String msg) {
		super(msg);
	}
}
