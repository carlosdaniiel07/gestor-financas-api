package com.carlos.gestorfinancas.services.exceptions;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
public class AuthorizationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String message) {
		super(message);
	}
}
