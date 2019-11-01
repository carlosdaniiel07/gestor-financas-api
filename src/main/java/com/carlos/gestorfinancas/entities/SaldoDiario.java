package com.carlos.gestorfinancas.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SaldoDiario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String banco;
	private String nomeConta;
	private double saldo;
	private Date data;
	
	public SaldoDiario() {
		super();
	}

	public SaldoDiario(Long id, String banco, String conta, double saldo, Date data) {
		super();
		this.id = id;
		this.banco = banco;
		this.nomeConta = conta;
		this.saldo = saldo;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getConta() {
		return nomeConta;
	}

	public void setConta(String conta) {
		this.nomeConta = conta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
