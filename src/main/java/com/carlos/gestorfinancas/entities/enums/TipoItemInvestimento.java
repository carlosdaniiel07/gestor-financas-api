package com.carlos.gestorfinancas.entities.enums;

public enum TipoItemInvestimento {

	APLICACAO(1, "Aplicação"),
	REINVESTIMENTO(2, "Reinvestimento"),
	RESGATE(3, "Resgate");
	
	private final int codigo;
	private final String descricao;
	
	TipoItemInvestimento(int codigo, String descricao){
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
