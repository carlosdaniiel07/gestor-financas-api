package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Projeto;
import com.carlos.gestorfinancas.entities.Subcategoria;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
public class CobrancaPagamentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cobranca cobranca;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataPagamento;
	
	private Conta conta;
	private double valorPago;
	private Categoria categoria;
	private Subcategoria subcategoria;
	private Projeto projeto;
	private Fatura fatura;
	
	public CobrancaPagamentoDTO() {
		super();
	}
	
	public CobrancaPagamentoDTO(com.carlos.gestorfinancas.entities.Cobranca cobranca, Date dataPagamento, Conta conta,
			double valorPago, Categoria categoria, Subcategoria subcategoria, Projeto projeto, Fatura fatura) {
		super();
		this.cobranca = cobranca;
		this.dataPagamento = dataPagamento;
		this.conta = conta;
		this.valorPago = valorPago;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.projeto = projeto;
		this.fatura = fatura;
	}

	public Cobranca getCobranca() {
		return cobranca;
	}

	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
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
}
