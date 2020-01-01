package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import com.carlos.gestorfinancas.entities.Corretora;
import com.carlos.gestorfinancas.entities.Investimento;
import com.carlos.gestorfinancas.entities.ModalidadeInvestimento;
import com.carlos.gestorfinancas.entities.enums.TipoInvestimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class InvestimentoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "É obrigatório informar a descrição")
	private String descricao;
	
	@NotNull(message = "É obrigatório informar o tipo de investimento")
	private TipoInvestimento tipo;
	
	@NotNull(message = "É obrigatório informar uma data de aplicação")
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataAplicacao;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataVencimento;
	
	@NotNull(message = "É obrigatório informar a modalidade do investimento")
	private ModalidadeInvestimento modalidade;
	
	@NotNull(message = "É obrigatório informar a corretora")
	private Corretora corretora;
	
	@Positive(message = "O valor da aplicação precisa ser positivo")
	private double valorAplicado;
	
	private String obs;

	public InvestimentoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public InvestimentoDTO(@NotEmpty(message = "É obrigatório informar a descrição") String descricao,
			@NotNull(message = "É obrigatório informar o tipo de investimento") TipoInvestimento tipo,
			@NotNull(message = "É obrigatório informar uma data de aplicação") Date dataAplicacao, Date dataVencimento,
			@NotNull(message = "É obrigatório informar a modalidade do investimento") ModalidadeInvestimento modalidade,
			@NotNull(message = "É obrigatório informar a corretora") Corretora corretora,
			@Positive(message = "O valor da aplicação precisa ser positivo") double valorAplicado, String obs) {
		super();
		this.descricao = descricao;
		this.tipo = tipo;
		this.dataAplicacao = dataAplicacao;
		this.dataVencimento = dataVencimento;
		this.modalidade = modalidade;
		this.corretora = corretora;
		this.valorAplicado = valorAplicado;
		this.obs = obs;
	}

	public Investimento toInvestimento() {
		return new Investimento(null, getDescricao(), getTipo(), getDataAplicacao(), null, null, getDataVencimento(), getModalidade(), getCorretora(), getValorAplicado(), 0, 0, getObs());
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

	public double getValorAplicado() {
		return valorAplicado;
	}

	public void setValorAplicado(double valorAplicado) {
		this.valorAplicado = valorAplicado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
}
