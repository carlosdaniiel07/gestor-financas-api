package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.TipoConta;
import com.carlos.gestorfinancas.services.validations.NovaConta;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
@NovaConta
public class ContaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar o nome da conta")
	private String nome;
	
	@NotEmpty(message = "É obrigatório informar o banco")
	private String banco;
	
	@NotEmpty(message = "É obrigatório informar a agência")
	private String agencia;
	
	@NotEmpty(message = "É obrigatório informar o número da conta")
	private String conta;
	
	private double saldoInicial;
	
	@NotNull(message = "É obrigatório informar o tipo da conta")
	private TipoConta tipo;
	
	public ContaDTO() {
		super();
	}

	public ContaDTO(String nome, String banco, String agencia, String conta, double saldoInicial, TipoConta tipoConta) {
		super();
		this.nome = nome;
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.saldoInicial = saldoInicial;
		this.tipo = tipoConta;
	}

	public Conta toConta() {
		return new Conta(null, getNome(), getBanco(), getAgencia(), getConta(), getSaldoInicial(), 0, true, getTipo());
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public TipoConta getTipo() {
		return tipo;
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}
}
