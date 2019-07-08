package com.carlos.gestorfinancas.entities.enums;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
public enum StatusFatura {
	NAO_FECHADA(1, "Não fechada"),
	PENDENTE(2, "Pendente"),
	PAGO(3, "Pago"),
	PAGO_PARCIAL(4, "Pago parcialmente");
	
	private final int codigo;
	private final String descricao;
	
	StatusFatura(int codigo, String descricao){
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
