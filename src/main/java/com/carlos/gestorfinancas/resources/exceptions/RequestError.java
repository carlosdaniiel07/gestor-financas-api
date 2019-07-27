package com.carlos.gestorfinancas.entities;

import java.io.Serializable;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
public class RequestError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final long timestamp;
	private final int httpStatus;
	private final String mensagem;
	
	public RequestError(long timestamp, int httpStatus, String mensagem) {
		super();
		this.timestamp = timestamp;
		this.httpStatus = httpStatus;
		this.mensagem = mensagem;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getMensagem() {
		return mensagem;
	}
}
