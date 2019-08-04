package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.carlos.gestorfinancas.entities.Categoria;
import com.carlos.gestorfinancas.entities.Subcategoria;
import com.carlos.gestorfinancas.services.validations.NovaSubcategoria;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
@NovaSubcategoria
public class SubcategoriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar o nome da subcategoria")
	private String nome;
	
	@NotNull(message = "É obrigatório informar a categoria que está relacionada")
	private Categoria categoria;

	public SubcategoriaDTO() {
		super();
	}

	public SubcategoriaDTO(String nome, Categoria categoria) {
		super();
		this.nome = nome;
		this.categoria = categoria;
	}

	public Subcategoria toSubcategoria() {
		return new Subcategoria(null, getNome(), true, true, getCategoria());
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
