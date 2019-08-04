package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.carlos.gestorfinancas.entities.Beneficiario;
import com.carlos.gestorfinancas.services.validations.NovoBeneficiario;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
@NovoBeneficiario
public class BeneficiarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "É obrigatório informar o nome do beneficiário")
	private String nome;
	
	@NotEmpty(message = "É obrigatório informar o banco do beneficiário")
	private String banco;
	
	@NotEmpty(message = "É obrigatório informar a agência do beneficiário")
	private String agencia;
	
	@NotEmpty(message = "É obrigatório informar a conta do beneficiário")
	private String conta;
	
	@PositiveOrZero(message = "O limite do beneficiário deve ser igual ou superior a 0 (zero)")
	private double limite;
	
	private String observacao;
	
	public BeneficiarioDTO() {
		super();
	}
	
	public BeneficiarioDTO(String nome, String banco, String agencia, String conta, double limite, String observacao) {
		super();
		this.nome = nome;
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.limite = limite;
		this.observacao = observacao;
	}

	public Beneficiario toBeneficiario() {
		return new Beneficiario(null, getNome(), getBanco(), getAgencia(), getConta(), getLimite(), getObservacao(), true);
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

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
