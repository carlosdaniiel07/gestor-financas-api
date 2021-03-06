package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.carlos.gestorfinancas.entities.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ModalidadeInvestimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	@Enumerated(EnumType.STRING)
	private TipoInvestimento tipo;
	
	private boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "modalidade")
	private List<Investimento> investimentos = new ArrayList<Investimento>();
	
	public ModalidadeInvestimento() {
		super();
	}
	
	public ModalidadeInvestimento(Long id, String nome, TipoInvestimento tipo, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.ativo = ativo;
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
	
	public TipoInvestimento getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoInvestimento tipo) {
		this.tipo = tipo;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<Investimento> getInvestimentos() {
		return investimentos;
	}

	public void setInvestimentos(List<Investimento> investimentos) {
		this.investimentos = investimentos;
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
		ModalidadeInvestimento other = (ModalidadeInvestimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
