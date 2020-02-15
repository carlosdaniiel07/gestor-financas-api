package com.carlos.gestorfinancas.dtos.nubank;

import java.io.Serializable;

public class NubankLoginResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String access_token;
	
	public NubankLoginResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public NubankLoginResponseDTO(String access_token) {
		super();
		this.access_token = access_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}
