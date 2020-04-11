package com.carlos.gestorfinancas.services.exceptions;

public abstract class ExternalServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExternalServiceException(String message) {
		super(message);
	}
}
