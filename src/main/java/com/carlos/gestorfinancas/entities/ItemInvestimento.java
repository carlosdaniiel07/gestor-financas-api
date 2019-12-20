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

import com.carlos.gestorfinancas.entities.enums.TipoItemInvestimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
public class ItemInvestimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoItemInvestimento tipo;
	
	private String descricao;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date data;
	
	private double valor;
	private double ir;
	private double iof;
	private double outrasTaxas;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "investimento_id")
	private Investimento investimento;

	public ItemInvestimento() {
		super();
	}
	
	public ItemInvestimento(Long id, TipoItemInvestimento tipo, String descricao, Date data, double valor, double ir,
			double iof, double outrasTaxas, Investimento investimento) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.data = data;
		this.valor = valor;
		this.ir = ir;
		this.iof = iof;
		this.outrasTaxas = outrasTaxas;
		this.investimento = investimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoItemInvestimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoItemInvestimento tipo) {
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

	public double getIr() {
		return ir;
	}

	public void setIr(double ir) {
		this.ir = ir;
	}

	public double getIof() {
		return iof;
	}

	public void setIof(double iof) {
		this.iof = iof;
	}

	public double getOutrasTaxas() {
		return outrasTaxas;
	}

	public void setOutrasTaxas(double outrasTaxas) {
		this.outrasTaxas = outrasTaxas;
	}

	public Investimento getInvestimento() {
		return investimento;
	}

	public void setInvestimento(Investimento investimento) {
		this.investimento = investimento;
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
		ItemInvestimento other = (ItemInvestimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
