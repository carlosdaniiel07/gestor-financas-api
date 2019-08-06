package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Entity
public class Fatura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String referencia;

	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private Date vencimento;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataPagamento;
	
	private double valor;
	private double valorPago;
	
	@Enumerated(EnumType.STRING)
	private StatusFatura status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cartao_id")
	private CartaoCredito cartao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "fatura", fetch = FetchType.LAZY)
	private List<Movimento> movimentos = new ArrayList<Movimento>();
	
	public Fatura() {
		super();
	}

	public Fatura(Long id, String referencia, Date vencimento, Date dataPagamento, double valor,
			double valorPago, StatusFatura status, CartaoCredito cartao) {
		super();
		this.id = id;
		this.referencia = referencia;
		this.vencimento = vencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.valorPago = valorPago;
		this.status = status;
		this.cartao = cartao;
	}

	/*
	 * Retorna o saldo restante a pagar da fatura
	 */
	public double getSaldoRestante() {
		return getValor() - getValorPago();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public StatusFatura getStatus() {
		return status;
	}

	public void setStatus(StatusFatura status) {
		this.status = status;
	}

	public CartaoCredito getCartao() {
		return cartao;
	}

	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
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
		Fatura other = (Fatura) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
