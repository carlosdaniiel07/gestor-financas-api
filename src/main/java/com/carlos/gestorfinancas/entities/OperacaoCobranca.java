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

import com.carlos.gestorfinancas.entities.enums.TipoOperacaoCobranca;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Entity
public class OperacaoCobranca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoOperacaoCobranca tipo;
	
	private String descricao;
	private Date data;
	private double valor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cobranca_id")
	private Cobranca cobranca;

	public OperacaoCobranca() {
		super();
	}
	
	public OperacaoCobranca(Long id, TipoOperacaoCobranca tipo, String descricao, Date data, double valor,
			Cobranca cobranca) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.cobranca = cobranca;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoOperacaoCobranca getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacaoCobranca tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Cobranca getCobranca() {
		return cobranca;
	}

	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
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
		OperacaoCobranca other = (OperacaoCobranca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
