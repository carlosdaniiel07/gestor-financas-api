package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IntegracaoTicket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Date dateParsed;
	private double valueParsed;
	private String description;
	
	private Date date;
	
	public IntegracaoTicket() {
		
	}

	public IntegracaoTicket(String id, Date dateParsed, double valueParsed, String description, Date date) {
		super();
		this.id = id;
		this.dateParsed = dateParsed;
		this.valueParsed = valueParsed;
		this.description = description;
		this.date = date;
	}

	public String getId() {
		return id;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegracaoTicket other = (IntegracaoTicket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
