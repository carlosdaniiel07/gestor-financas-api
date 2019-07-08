package com.carlos.gestorfinancas.entities.enums;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
public enum StatusTransferencia {
	EFETIVADO(1, "Efetivado"),
	AGENDADO(2, "Agendado"),
	ESTORNADO(3, "Estornado");
	
	private final int codigo;
	private final String descricao;
	
	StatusTransferencia(int codigo, String descricao){
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
