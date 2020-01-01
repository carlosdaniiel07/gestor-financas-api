package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.carlos.gestorfinancas.entities.Investimento;
import com.carlos.gestorfinancas.entities.ItemInvestimento;

public class AplicacaoResgateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "É obrigatório informar os dados do investimento")
	private Investimento investimento;
	
	@NotNull(message = "É obrigatório informar os dados da aplicação/resgate")
	private ItemInvestimento item;
	
	public AplicacaoResgateDTO() {
		// TODO Auto-generated constructor stub
	}

	public AplicacaoResgateDTO(
			@NotNull(message = "É obrigatório informar os dados do investimento") Investimento investimento,
			@NotNull(message = "É obrigatório informar os dados da aplicação/resgate") ItemInvestimento item) {
		super();
		this.investimento = investimento;
		this.item = item;
	}
	
	public Investimento getInvestimento() {
		return investimento;
	}

	public void setInvestimento(Investimento investimento) {
		this.investimento = investimento;
	}

	public ItemInvestimento getItem() {
		return item;
	}

	public void setItem(ItemInvestimento item) {
		this.item = item;
	}
}
