package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Projeto;
import com.carlos.gestorfinancas.entities.Subcategoria;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 20/07/2019
 */
public class CobrancaRemocaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cobranca cobranca;
	private Conta conta;
	private Categoria categoria;
	private Subcategoria subcategoria;
	private Projeto projeto;
	
	public CobrancaRemocaoDTO() {
		super();
	}
	
	public CobrancaRemocaoDTO(Cobranca cobranca, Conta conta, Categoria categoria, Subcategoria subcategoria,
			Projeto projeto) {
		super();
		this.cobranca = cobranca;
		this.conta = conta;
		this.categoria = categoria;
		this.subcategoria = subcategoria;
		this.projeto = projeto;
	}

	public Cobranca getCobranca() {
		return cobranca;
	}

	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
}
