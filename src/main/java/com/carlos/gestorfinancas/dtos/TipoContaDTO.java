package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.carlos.gestorfinancas.entities.TipoConta;
import com.carlos.gestorfinancas.services.validations.NovoTipoConta;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 21/07/2019
 */
@NovoTipoConta
public class TipoContaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar o nome do tipo de conta")
	private String nome;

	public TipoContaDTO() {
		super();
	}

	public TipoContaDTO(String nome) {
		super();
		this.nome = nome;
	}
	
	public TipoConta toTipoConta() {
		return new TipoConta(null, getNome(), true);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
