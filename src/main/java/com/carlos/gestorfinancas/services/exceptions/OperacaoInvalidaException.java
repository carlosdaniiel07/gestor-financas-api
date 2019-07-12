package com.carlos.gestorfinancas.services.exceptions;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
public class OperacaoInvalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public OperacaoInvalidaException(String message) {
		super(message);
	}
}
