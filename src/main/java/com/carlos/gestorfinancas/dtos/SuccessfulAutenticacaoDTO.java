package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import com.carlos.gestorfinancas.entities.Usuario;

public class SuccessfulAutenticacaoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private String accessToken;
	
	public SuccessfulAutenticacaoDTO() {
		
	}

	public SuccessfulAutenticacaoDTO(Usuario usuario, String accessToken) {
		super();
		this.usuario = usuario;
		this.accessToken = accessToken;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
