package com.carlos.gestorfinancas.entities.enums;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 06/07/2019
 */
public enum StatusProjeto {
	EM_ANDAMENTO(1, "Em andamento"),
	FINALIZADO(2, "Finalizado"),
	PARADO(3, "Parado"),
	CANCELADO(4, "Cancelado");
	
	private final int codigo;
	private final String descricao;
	
	StatusProjeto(int codigo, String descricao) {
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
