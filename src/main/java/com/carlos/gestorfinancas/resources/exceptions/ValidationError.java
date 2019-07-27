package com.carlos.gestorfinancas.resources.exceptions;

import java.util.HashSet;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 27/07/2019
 */
public class ValidationError extends RequestError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashSet<String> errors = new HashSet<String>();
	
	/**
	 * @param timestamp
	 * @param httpStatus
	 * @param mensagem
	 */
	public ValidationError(long timestamp, int httpStatus, String mensagem) {		
		super(timestamp, httpStatus, mensagem);
	}

	public HashSet<String> getErrors() {
		return errors;
	}
	
	public void addError(String errorMessage) {
		this.errors.add(errorMessage);
	}
}
