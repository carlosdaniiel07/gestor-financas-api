package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.carlos.gestorfinancas.entities.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
public class Investimento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private TipoInvestimento tipo;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataAplicacao;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataReinvestimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataResgate;
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	private Date dataVencimento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "modalidade_id")
	private ModalidadeInvestimento modalidade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "corretora_id")
	private Corretora corretora;
	
	@OneToMany(mappedBy = "investimento", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ItemInvestimento> itens = new ArrayList<ItemInvestimento>();
	
	private double valorAplicado;
	private double valorAtual;
	private double valorResgatado;
	private String obs;
	
	public Investimento() {
		super();
	}
	
	public Investimento(Long id, String descricao, TipoInvestimento tipo, Date dataAplicacao, Date dataReinvestimento,
			Date dataResgate, Date dataVencimento, ModalidadeInvestimento modalidade, Corretora corretora,
			double valorAplicado, double valorAtual, double valorResgatado, String obs) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.dataAplicacao = dataAplicacao;
		this.dataReinvestimento = dataReinvestimento;
		this.dataResgate = dataResgate;
		this.dataVencimento = dataVencimento;
		this.modalidade = modalidade;
		this.corretora = corretora;
		this.valorAplicado = valorAplicado;
		this.valorAtual = valorAtual;
		this.valorResgatado = valorResgatado;
		this.obs = obs;
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

	public TipoInvestimento getTipo() {
		return tipo;
	}

	public void setTipo(TipoInvestimento tipo) {
		this.tipo = tipo;
	}

	public Date getDataAplicacao() {
		return dataAplicacao;
	}

	public void setDataAplicacao(Date dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}

	public Date getDataReinvestimento() {
		return dataReinvestimento;
	}

	public void setDataReinvestimento(Date dataReinvestimento) {
		this.dataReinvestimento = dataReinvestimento;
	}

	public Date getDataResgate() {
		return dataResgate;
	}

	public void setDataResgate(Date dataResgate) {
		this.dataResgate = dataResgate;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public ModalidadeInvestimento getModalidade() {
		return modalidade;
	}

	public void setModalidade(ModalidadeInvestimento modalidade) {
		this.modalidade = modalidade;
	}

	public Corretora getCorretora() {
		return corretora;
	}

	public void setCorretora(Corretora corretora) {
		this.corretora = corretora;
	}

	public List<ItemInvestimento> getItens() {
		return itens;
	}

	public void setItens(List<ItemInvestimento> itens) {
		this.itens = itens;
	}

	public double getValorAplicado() {
		return valorAplicado;
	}

	public void setValorAplicado(double valorAplicado) {
		this.valorAplicado = valorAplicado;
	}

	public double getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(double valorAtual) {
		this.valorAtual = valorAtual;
	}

	public double getValorResgatado() {
		return valorResgatado;
	}

	public void setValorResgatado(double valorResgatado) {
		this.valorResgatado = valorResgatado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
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
		Investimento other = (Investimento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
