package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.carlos.gestorfinancas.entities.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 06/07/2019
 */
@Entity
public class Usuario implements Serializable, UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	private String login;
	
	@JsonIgnore
	private String senha;
	
	private String email;
		
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipo;
	
	private boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<LogAcesso> logAcessos = new ArrayList<LogAcesso>();

	public Usuario() {
		super();
	}

	public Usuario(Long id, String nome, String login, String senha, String email, boolean ativo, TipoUsuario tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.ativo = ativo;
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<LogAcesso> getLogAcessos() {
		return logAcessos;
	}

	public void setLogAcessos(List<LogAcesso> logAcessos) {
		this.logAcessos = logAcessos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// Obtém o perfíl/role do usuário
	@JsonIgnore
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(getTipo().getDescricao()));
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return getSenha();
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return getLogin();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isAtivo();
	}
}
