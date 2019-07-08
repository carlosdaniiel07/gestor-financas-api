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
public class CartaoCredito implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private String bandeira;
	private int diaFechamento;
	private int diaPagamento;
	private double limite;
	private boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cartao", fetch = FetchType.LAZY)
	private List<Fatura> faturas = new ArrayList<Fatura>();
	
	public CartaoCredito() {
		super();
	}

	public CartaoCredito(Long id, String nome, String bandeira, int diaFechamento, int diaPagamento, double limite,
			boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.bandeira = bandeira;
		this.diaFechamento = diaFechamento;
		this.diaPagamento = diaPagamento;
		this.limite = limite;
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
	
	public String getBandeira() {
		return bandeira;
	}
	
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	
	public int getDiaFechamento() {
		return diaFechamento;
	}
	
	public void setDiaFechamento(int diaFechamento) {
		this.diaFechamento = diaFechamento;
	}
	
	public int getDiaPagamento() {
		return diaPagamento;
	}
	
	public void setDiaPagamento(int diaPagamento) {
		this.diaPagamento = diaPagamento;
	}
	
	public double getLimite() {
		return limite;
	}
	
	public void setLimite(double limite) {
		this.limite = limite;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<Fatura> getFaturas() {
		return faturas;
	}

	public void setFaturas(List<Fatura> faturas) {
		this.faturas = faturas;
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
		CartaoCredito other = (CartaoCredito) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
