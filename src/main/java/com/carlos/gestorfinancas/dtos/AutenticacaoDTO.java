package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
public class AutenticacaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String loginOuEmail;
	private String senha;
	
	public AutenticacaoDTO() {
		
	}
	
	/**
	 * @param loginOuEmail
	 * @param senha
	 */
	public AutenticacaoDTO(String loginOuEmail, String senha) {
		super();
		this.loginOuEmail = loginOuEmail;
		this.senha = senha;
	}

	/**
	 * @return the loginOuEmail
	 */
	public String getLoginOuEmail() {
		return loginOuEmail;
	}

	/**
	 * @param loginOuEmail the loginOuEmail to set
	 */
	public void setLoginOuEmail(String loginOuEmail) {
		this.loginOuEmail = loginOuEmail;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
