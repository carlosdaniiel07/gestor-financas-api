package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Entity
public class Movimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String descricao;
	private char tipo;
	private Date dataInclusao;
	private Date dataContabilizacao;
	private double valor;
	private double acrescimo;
	private double decrescimo;
	
	@Enumerated(EnumType.STRING)
	private StatusMovimento status;
	
	private String origem;
	private String observacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "conta_id")
	private Conta conta;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategoria_id")
	private Subcategoria subcategoria;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "projeto_id")
	private Projeto projeto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fatura_id")
	private Fatura fatura;

	public Movimento() {
		super();
	}

	public Movimento(Long id, String descricao, char tipo, Date dataInclusao, Date dataContabilizacao, double valor,
			double acrescimo, double decrescimo, StatusMovimento status, String origem, String observacao, Conta conta,
			Categoria categoria, Subcategoria subcategoria, Projeto projeto, Fatura fatura) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.dataInclusao = dataInclusao;
		this.dataContabilizacao = dataContabilizacao;
		this.valor = valor;
		this.acrescimo = acrescimo;
		this.decrescimo = decrescimo;
		this.status = status;
		this.origem = origem;
		this.observacao = observacao;
		this.conta = conta;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.projeto = projeto;
		this.fatura = fatura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataContabilizacao() {
		return dataContabilizacao;
	}

	public void setDataContabilizacao(Date dataContabilizacao) {
		this.dataContabilizacao = dataContabilizacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getAcrescimo() {
		return acrescimo;
	}

	public void setAcrescimo(double acrescimo) {
		this.acrescimo = acrescimo;
	}

	public double getDecrescimo() {
		return decrescimo;
	}

	public void setDecrescimo(double decrescimo) {
		this.decrescimo = decrescimo;
	}

	public StatusMovimento getStatus() {
		return status;
	}

	public void setStatus(StatusMovimento status) {
		this.status = status;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
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
		Movimento other = (Movimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
