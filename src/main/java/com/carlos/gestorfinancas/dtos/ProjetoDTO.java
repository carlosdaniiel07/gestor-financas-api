package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import com.carlos.gestorfinancas.entities.Projeto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
public class ProjetoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar o nome do projeto")
	private String nome;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	@FutureOrPresent(message = "A data final do projeto não pode ser retroativa")
	private Date dataFinal;

	@PositiveOrZero(message = "O orçamento do projeto não pode ser negativo")
	private Double orcamento;
	
	private String descricao;
	
	public ProjetoDTO() {
		super();
	}

	public ProjetoDTO(String nome, Date dataFinal, Double orcamento, String descricao) {
		super();
		this.nome = nome;
		this.dataFinal = dataFinal;
		this.orcamento = orcamento;
		this.descricao = descricao;
	}
	
	public Projeto toProjeto() {
		return new Projeto(null, getNome(), null, getDataFinal(), getOrcamento(), getDescricao(), null, true);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Double orcamento) {
		this.orcamento = orcamento;
	}
}
