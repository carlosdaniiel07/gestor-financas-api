package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.Projeto;
import com.carlos.gestorfinancas.entities.Subcategoria;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
public class MovimentoDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar uma descrição para o movimento")
	private String descricao;
	
	private char tipo;
	
	@NotNull(message = "É obrigatório informar uma data de contabilização")
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dataContabilizacao;
	
	@Positive(message = "O valor precisa ser positivo")
	private double valor;
	
	@PositiveOrZero(message = "O acréscimo precisa ser positivo")
	private double acrescimo;
	
	@PositiveOrZero(message = "O decréscimo precisa ser positivo")
	private double decrescimo;
	
	@NotNull(message = "É obrigatório informar o status do movimento")
	private StatusMovimento status;
	
	private String observacao;
	
	@NotNull(message = "É obrigatório informar uma conta para contabilização")
	private Conta conta;
	
	private Categoria categoria;
	private Subcategoria subcategoria;
	private Projeto projeto;
	private Fatura fatura;

	/**
	 * 
	 */
	public MovimentoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param descricao
	 * @param tipo
	 * @param dataInclusao
	 * @param dataContabilizacao
	 * @param valor
	 * @param acrescimo
	 * @param decrescimo
	 * @param status
	 * @param observacao
	 * @param conta
	 * @param categoria
	 * @param subcategoria
	 * @param projeto
	 * @param fatura
	 */
	public MovimentoDTO(String descricao, char tipo, Date dataContabilizacao, double valor,
			double acrescimo, double decrescimo, StatusMovimento status, String observacao, Conta conta,
			Categoria categoria, Subcategoria subcategoria, Projeto projeto, Fatura fatura) {
		super();
		this.descricao = descricao;
		this.tipo = tipo;
		this.dataContabilizacao = dataContabilizacao;
		this.valor = valor;
		this.acrescimo = acrescimo;
		this.decrescimo = decrescimo;
		this.status = status;
		this.observacao = observacao;
		this.conta = conta;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.projeto = projeto;
		this.fatura = fatura;
	}
	
	public Movimento toMovimento() {
		return new Movimento(null, getDescricao(), getTipo(), null, getDataContabilizacao(), getValor(), getAcrescimo(), getDecrescimo(), 
				getStatus(), null, getObservacao(), getConta(), getCategoria(), getSubcategoria(), getProjeto(), getFatura());
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the tipo
	 */
	public char getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the dataContabilizacao
	 */
	public Date getDataContabilizacao() {
		return dataContabilizacao;
	}

	/**
	 * @param dataContabilizacao the dataContabilizacao to set
	 */
	public void setDataContabilizacao(Date dataContabilizacao) {
		this.dataContabilizacao = dataContabilizacao;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * @return the acrescimo
	 */
	public double getAcrescimo() {
		return acrescimo;
	}

	/**
	 * @param acrescimo the acrescimo to set
	 */
	public void setAcrescimo(double acrescimo) {
		this.acrescimo = acrescimo;
	}

	/**
	 * @return the decrescimo
	 */
	public double getDecrescimo() {
		return decrescimo;
	}

	/**
	 * @param decrescimo the decrescimo to set
	 */
	public void setDecrescimo(double decrescimo) {
		this.decrescimo = decrescimo;
	}

	/**
	 * @return the status
	 */
	public StatusMovimento getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusMovimento status) {
		this.status = status;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * @param observacao the observacao to set
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * @return the conta
	 */
	public Conta getConta() {
		return conta;
	}

	/**
	 * @param conta the conta to set
	 */
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the subcategoria
	 */
	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	/**
	 * @param subcategoria the subcategoria to set
	 */
	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	/**
	 * @return the projeto
	 */
	public Projeto getProjeto() {
		return projeto;
	}

	/**
	 * @param projeto the projeto to set
	 */
	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	/**
	 * @return the fatura
	 */
	public Fatura getFatura() {
		return fatura;
	}

	/**
	 * @param fatura the fatura to set
	 */
	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}
}
