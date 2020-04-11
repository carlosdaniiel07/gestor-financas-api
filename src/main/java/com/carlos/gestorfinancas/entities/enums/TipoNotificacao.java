package com.carlos.gestorfinancas.entities.enums;

public enum TipoNotificacao {
	
	PUSH(1, "Push notification");
	
	private final int codigo;
	private final String descricao;
	
	private TipoNotificacao(int codigo, String descricao) {
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
