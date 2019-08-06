package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Fatura;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 05/08/2019
 */
public class FaturaPagamentoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Fatura fatura;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataPagamento;
	
	private Conta conta;
	private double valorPago;
	
	/**
	 * 
	 */
	public FaturaPagamentoDTO() {
		super();
	}
	/**
	 * @param fatura
	 * @param dataPagamento
	 * @param conta
	 * @param valorPago
	 * @param categoria
	 * @param subcategoria
	 * @param projeto
	 */
	public FaturaPagamentoDTO(Fatura fatura, Date dataPagamento, Conta conta, double valorPago) {
		super();
		this.fatura = fatura;
		this.dataPagamento = dataPagamento;
		this.conta = conta;
		this.valorPago = valorPago;
	}
	/**
	 * @return the fatura
	 */
	public Fatura getFatura() {
		return fatura;
	}
	/**
	 * @param fatura the fatura to set
	 */
	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}
	/**
	 * @return the dataPagamento
	 */
	public Date getDataPagamento() {
		return dataPagamento;
	}
	/**
	 * @param dataPagamento the dataPagamento to set
	 */
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	/**
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}
	/**
	 * @param conta the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	/**
	 * @return the valorPago
	 */
	public double getValorPago() {
		return valorPago;
	}
	/**
	 * @param valorPago the valorPago to set
	 */
	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
}
