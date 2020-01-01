package com.carlos.gestorfinancas.entities.enums;

public enum TipoInvestimento {

	RENDA_FIXA(1, "Renda fixa"),
	RENDA_VARIAVEL(2, "Renda variável");
	
	private final int codigo;
	private final String descricao;
	
	TipoInvestimento(int codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
