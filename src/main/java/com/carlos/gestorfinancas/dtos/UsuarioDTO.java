package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.carlos.gestorfinancas.entities.Usuario;
import com.carlos.gestorfinancas.entities.enums.TipoUsuario;
import com.carlos.gestorfinancas.services.validations.NovoUsuario;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 08/08/2019
 */
@NovoUsuario
public class UsuarioDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message =  "É obrigatório informar o nome do usuário")
	private String nome;
	
	@NotEmpty(message =  "É obrigatório informar o login do usuário")
	private String login;
	
	@NotEmpty(message =  "É obrigatório informar a senha do usuário")
	private String senha;
	
	@NotEmpty(message =  "É obrigatório informar o e-mail do usuário")
	private String email;
	
	@NotNull(message = "É obrigatório informar o tipo de usuário (usuário comum ou administrador)")
	private TipoUsuario tipo;

	public UsuarioDTO() {
		
	}
	
	/**
	 * @param nome
	 * @param login
	 * @param senha
	 * @param email
	 */
	public UsuarioDTO(@NotEmpty(message = "É obrigatório informar o nome do usuário") String nome,
			@NotEmpty(message = "É obrigatório informar o login do usuário") String login,
			@NotEmpty(message = "É obrigatório informar a senha do usuário") String senha,
			@NotEmpty(message = "É obrigatório informar o e-mail do usuário") String email, TipoUsuario tipo) {
		super();
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.tipo = tipo;
	}

	public Usuario toUsuario() {
		return new Usuario(null, getNome(), getLogin(), getSenha(), getEmail(), null, true, getTipo());
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the tipo
	 */
	public TipoUsuario getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
}
