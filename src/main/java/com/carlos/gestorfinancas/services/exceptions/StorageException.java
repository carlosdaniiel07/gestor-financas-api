package com.carlos.gestorfinancas.services.exceptions;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
public class StorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public StorageException(String msg) {
		super(msg);
	}
}
