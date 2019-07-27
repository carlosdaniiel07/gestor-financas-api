package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.carlos.gestorfinancas.entities.Beneficiario;
import com.carlos.gestorfinancas.entities.Cobranca;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
public class CobrancaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "É obrigatório informar a descrição da cobrança")
	private String descricao;
	
	private int parcela;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = "É obrigatório informar a data de vencimento da cobrança")
	private Date dataVencimento;
	
	@PositiveOrZero(message = "O valor deve ser igual ou superior a 0 (zero)")
	private double valor;
	
	@PositiveOrZero(message = "O valor do juros deve ser igual ou superior a 0 (zero)")
	private double juros;
	
	@PositiveOrZero(message = "O valor do desconto deve ser igual ou superior a 0 (zero)")
	private double desconto;
	
	private String observacao;
	
	@NotNull(message = "É obrigatório informar o beneficiário da cobrança")
	private Beneficiario beneficiario;

	public CobrancaDTO() {
		super();
	}

	public CobrancaDTO(@NotEmpty(message = "É obrigatório informar a descrição da cobrança") String descricao,
			int parcela, @FutureOrPresent Date dataVencimento, double valor, double juros, double desconto,
			String observacao, Beneficiario beneficiario) {
		super();
		this.descricao = descricao;
		this.parcela = parcela;
		this.dataVencimento = dataVencimento;
		this.valor = valor;
		this.juros = juros;
		this.desconto = desconto;
		this.observacao = observacao;
		this.beneficiario = beneficiario;
	}
	
	public Cobranca toCobranca() {
		return new Cobranca(null, getDescricao(), getParcela(), getDataVencimento(), null, null, getValor(), getJuros(), getDesconto(), 0, getObservacao(), null, getBeneficiario());
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getJuros() {
		return juros;
	}

	public void setJuros(double juros) {
		this.juros = juros;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
}
