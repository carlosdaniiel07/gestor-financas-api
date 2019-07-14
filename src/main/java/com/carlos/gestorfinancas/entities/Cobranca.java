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

import com.carlos.gestorfinancas.entities.enums.StatusCobranca;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Entity
public class Cobranca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String descricao;
	private int parcela;
	private Date dataVencimento;
	private Date dataAgendamento;
	private Date dataPagamento;
	private double valor;
	private double juros;
	private double desconto;
	private double saldo;
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private StatusCobranca status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "beneficiario_id")
	private Beneficiario beneficiario;

	@JsonIgnore
	@OneToMany(mappedBy = "cobranca", fetch = FetchType.LAZY)
	private List<OperacaoCobranca> operacoes = new ArrayList<OperacaoCobranca>();
	
	public Cobranca() {
		super();
	}
	
	public Cobranca(Long id, String descricao, int parcela, Date dataVencimento, Date dataAgendamento,
			Date dataPagamento, double valor, double juros, double desconto, double saldo, String observacao,
			StatusCobranca status, Beneficiario beneficiario) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.parcela = parcela;
		this.dataVencimento = dataVencimento;
		this.dataAgendamento = dataAgendamento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.juros = juros;
		this.desconto = desconto;
		this.saldo = saldo;
		this.observacao = observacao;
		this.status = status;
		this.beneficiario = beneficiario;
	}

	public double getValorTotal() {
		return this.getValor() + this.getJuros() - this.getDesconto();
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

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
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

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public StatusCobranca getStatus() {
		return status;
	}

	public void setStatus(StatusCobranca status) {
		this.status = status;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	public List<OperacaoCobranca> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(List<OperacaoCobranca> operacoes) {
		this.operacoes = operacoes;
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
		Cobranca other = (Cobranca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
