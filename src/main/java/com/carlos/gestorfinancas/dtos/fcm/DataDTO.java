package com.carlos.gestorfinancas.dtos.fcm;

import java.io.Serializable;

public class DataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	public DataDTO() {
		
	}

	public DataDTO(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
