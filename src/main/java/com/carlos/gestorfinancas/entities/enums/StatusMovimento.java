package com.carlos.gestorfinancas.entities.enums;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
public enum StatusMovimento {
	EFETIVADO(1, "Efetivado"),
	PENDENTE(2, "Pendente"),
	AGENDADO(3, "Agendado");
	
	private final int codigo;
	private final String descricao;
	
	StatusMovimento(int codigo, String descricao){
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
