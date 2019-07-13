package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Entity
public class TipoConta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tipo", fetch = FetchType.LAZY)
	private List<Conta> contas = new ArrayList<Conta>();
	
	public TipoConta() {
		super();
	}

	public TipoConta(Long id, String nome, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.ativo = ativo;
	}
	
	/**
	 * Retorna o saldo do tipo da conta (soma os saldos de todas as contas pertencentes)
	 * @return
	 */
	@JsonIgnore
	public double getSaldo() {
		double saldoTotal = 0;
		List<Conta> contas = this.getContas();
		
		for (Conta conta : contas) {
			saldoTotal += conta.getSaldo();
		}
		
		return saldoTotal;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
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
		TipoConta other = (TipoConta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
