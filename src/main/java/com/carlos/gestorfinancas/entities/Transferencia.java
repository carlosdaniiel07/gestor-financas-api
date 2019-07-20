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

import com.carlos.gestorfinancas.entities.enums.StatusTransferencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
@Entity
public class Transferencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String descricao;
	private double valor;
	private double taxa;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataInclusao;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataContabilizacao;
	
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private StatusTransferencia status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "origem_id")
	private Conta contaOrigem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destino_id")
	private Conta contaDestino;

	public Transferencia() {
		super();
	}

	public Transferencia(Long id, String descricao, double valor, double taxa, Date dataInclusao,
			Date dataContabilizacao, String observacao, StatusTransferencia status, Conta contaOrigem,
			Conta contaDestino) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.taxa = taxa;
		this.dataInclusao = dataInclusao;
		this.dataContabilizacao = dataContabilizacao;
		this.observacao = observacao;
		this.status = status;
		this.contaOrigem = contaOrigem;
		this.contaDestino = contaDestino;
	}

	/**
	 * Verifica se a transferência está efetivada
	 * @return
	 */
	@JsonIgnore
	public boolean isEfetivado() {
		return this.getStatus() == StatusTransferencia.EFETIVADO;
	}
	
	/**
	 * Verifica se a transferência é futura em relação a uma dada data
	 * @param data => A data a ser comparada
	 * @return
	 */
	@JsonIgnore
	public boolean isFuturo(Date data) {
		return this.getDataContabilizacao().after(data);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
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
		Transferencia other = (Transferencia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
