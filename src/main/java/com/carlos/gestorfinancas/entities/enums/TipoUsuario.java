package com.carlos.gestorfinancas.entities.enums;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/07/2019
 */
public enum TipoUsuario {
	USUARIO(1, "ROLE_USUARIO"),
	ADM(2, "ROLE_ADM");
	
	private final int codigo;
	private final String descricao;
	
	TipoUsuario(int codigo, String descricao){
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
