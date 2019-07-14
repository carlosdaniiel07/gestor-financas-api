package com.carlos.gestorfinancas.entities.enums;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
public enum TipoOperacaoCobranca {
	BAIXA(1, "Baixa");
	
	private final int codigo;
	private final String descricao;
	
	TipoOperacaoCobranca(int codigo, String descricao){
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
