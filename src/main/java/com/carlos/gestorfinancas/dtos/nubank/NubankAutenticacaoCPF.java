package com.carlos.gestorfinancas.dtos.nubank;

import java.io.Serializable;

public class NubankAutenticacaoCPF implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String grant_type;
	private String login;
	private String password;
	private String client_id;
	private String client_secret;
	
	public NubankAutenticacaoCPF() {
		// TODO Auto-generated constructor stub
	}
	
	public NubankAutenticacaoCPF(String grant_type, String login, String password, String client_id,
			String client_secret) {
		super();
		this.grant_type = grant_type;
		this.login = login;
		this.password = password;
		this.client_id = client_id;
		this.client_secret = client_secret;
	}
	
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
}
