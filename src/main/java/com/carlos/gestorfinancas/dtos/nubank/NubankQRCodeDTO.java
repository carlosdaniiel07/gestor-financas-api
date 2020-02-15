package com.carlos.gestorfinancas.dtos.nubank;

import java.io.Serializable;
import java.util.Date;

public class NubankQRCodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private Date geradoEm;
	
	public NubankQRCodeDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public NubankQRCodeDTO(String uuid, Date geradoEm) {
		super();
		this.uuid = uuid;
		this.geradoEm = geradoEm;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getGeradoEm() {
		return geradoEm;
	}

	public void setGeradoEm(Date geradoEm) {
		this.geradoEm = geradoEm;
	}
}
