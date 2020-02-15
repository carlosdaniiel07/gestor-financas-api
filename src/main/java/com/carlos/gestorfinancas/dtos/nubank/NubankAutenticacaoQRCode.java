package com.carlos.gestorfinancas.dtos.nubank;

import java.io.Serializable;

public class NubankAutenticacaoQRCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String qr_code_id;
	private String type;
	
	public NubankAutenticacaoQRCode() {
		// TODO Auto-generated constructor stub
	}

	public NubankAutenticacaoQRCode(String qr_code_id, String type) {
		super();
		this.qr_code_id = qr_code_id;
		this.type = type;
	}

	public String getQr_code_id() {
		return qr_code_id;
	}

	public void setQr_code_id(String qr_code_id) {
		this.qr_code_id = qr_code_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
