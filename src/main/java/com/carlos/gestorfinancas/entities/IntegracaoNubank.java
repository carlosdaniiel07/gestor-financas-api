package com.carlos.gestorfinancas.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IntegracaoNubank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String transactionId;

	private String description;
	private String category;
	private double amount;
	private double amount_without_iof;
	private Date time;
	private String title;
	
	private Date date;

	
	public IntegracaoNubank() {
		// TODO Auto-generated constructor stub
	}
	
	public IntegracaoNubank(String transactionId, String description, String category, double amount,
			double amount_without_iof, Date time, String title, Date date) {
		super();
		this.transactionId = transactionId;
		this.description = description;
		this.category = category;
		this.amount = amount;
		this.amount_without_iof = amount_without_iof;
		this.time = time;
		this.title = title;
		this.date = date;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount_without_iof() {
		return amount_without_iof;
	}

	public void setAmount_without_iof(double amount_without_iof) {
		this.amount_without_iof = amount_without_iof;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
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
		IntegracaoNubank other = (IntegracaoNubank) obj;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
}
