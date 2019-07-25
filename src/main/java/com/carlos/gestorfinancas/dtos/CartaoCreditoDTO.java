package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.carlos.gestorfinancas.entities.CartaoCredito;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
public class CartaoCreditoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar o nome do cartão de crédito")
	private String nome;
	
	@NotEmpty(message = "É obrigatório informar a bandeira do cartão de crédito")
	private String bandeira;
	
	@Min(value = 1L, message = "O valor mínimo para o dia de fechamento é 1")
	@Max(value = 31L, message = "O valor máximo para o dia de fechamento é 31")
	private int diaFechamento;
	
	@Min(value = 1L, message = "O valor mínimo para o dia de pagamento é 1")
	@Max(value = 31L, message = "O valor máximo para o dia de pagamento é 31")
	private int diaPagamento;
	
	@PositiveOrZero(message = "O limite do cartão de crédito deve ser igual ou superior a 0 (zero)")
	private double limite;
	
	public CartaoCreditoDTO() {
		super();
	}

	public CartaoCreditoDTO(String nome, String bandeira, int diaFechamento, int diaPagamento, double limite) {
		super();
		this.nome = nome;
		this.bandeira = bandeira;
		this.diaFechamento = diaFechamento;
		this.diaPagamento = diaPagamento;
		this.limite = limite;
	}
	
	public CartaoCredito toCartaoCredito() {
		return new CartaoCredito(null, getNome(), getBandeira(), getDiaFechamento(), getDiaPagamento(), getLimite(), true);
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
}
