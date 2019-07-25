package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Transferencia;
import com.carlos.gestorfinancas.entities.enums.StatusTransferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
public class TransferenciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar a descrição da transferência")
	private String descricao;
	
	@NotNull(message = "É obrigatório informar o valor da transferência ")
	@PositiveOrZero(message = "O valor da transferência não pode ser negativo")
	private double valor;
	
	@PositiveOrZero(message = "O valor da taxa não pode ser negativo")
	private double taxa;

	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	@NotNull(message = "É obrigatório informar a data de contabilização")
	private Date dataContabilizacao;
	
	private String observacao;
	
	@NotNull(message = "É obrigatório informar o status da transferência")
	private StatusTransferencia status;
	
	@NotNull(message = "É obrigatório informar a conta de origem")
	private Conta contaOrigem;
	
	@NotNull(message = "É obrigatório informar a conta de destino")
	private Conta contaDestino;

	public TransferenciaDTO() {
		super();
	}
	
	public TransferenciaDTO(String descricao, double valor, double taxa, Date dataContabilizacao, String observacao, StatusTransferencia status, 
			Conta contaOrigem, Conta contaDestino) {
		super();
		this.descricao = descricao;
		this.valor = valor;
		this.taxa = taxa;
		this.dataContabilizacao = dataContabilizacao;
		this.observacao = observacao;
		this.status = status;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
	}

	public Transferencia toTransferencia() {
		return new Transferencia(null, getDescricao(), getValor(), getTaxa(), null, getDataContabilizacao(), getObservacao(), getStatus(), getContaOrigem(), getContaDestino());
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public Date getDataContabilizacao() {
		return dataContabilizacao;
	}

	public void setDataContabilizacao(Date dataContabilizacao) {
		this.dataContabilizacao = dataContabilizacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public StatusTransferencia getStatus() {
		return status;
	}

	public void setStatus(StatusTransferencia status) {
		this.status = status;
	}

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}	
}
