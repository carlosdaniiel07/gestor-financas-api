package com.carlos.gestorfinancas.dtos.ticket;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Date dateParsed;
	private double valueParsed;
	private String description;
	private char tipo;
	
	public Transaction() {
		
	}

	public Transaction(String id, Date dateParsed, double valueParsed, String description, char tipo) {
		super();

		this.dateParsed = dateParsed;
		this.valueParsed = valueParsed;
		this.description = description;
		this.tipo = tipo;
		this.id = this.buildIdCode();
	}
	
	public boolean isDebito() {
		return this.tipo == 'D';
	}
	
	public String buildIdCode() {
		return String.valueOf(this.getDateParsed().getTime()) + String.valueOf(this.getValueParsed()) + this.getDescription();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateParsed() {
		return dateParsed;
	}

	public void setDateParsed(Date dateParsed) {
		this.dateParsed = dateParsed;
	}

	public double getValueParsed() {
		return valueParsed;
	}

	public void setValueParsed(double valueParsed) {
		this.valueParsed = valueParsed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
}
