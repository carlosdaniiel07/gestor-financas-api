package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.carlos.gestorfinancas.entities.Categoria;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
public class CategoriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "É obrigatório informar o nome da categoria")
	private String nome;
	
	private char tipo;
	
	public CategoriaDTO() {
		super();
	}
	
	public CategoriaDTO(String nome, char tipo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public Categoria toCategoria() {
		return new Categoria(null, getNome(), getTipo(), true, true);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
}
