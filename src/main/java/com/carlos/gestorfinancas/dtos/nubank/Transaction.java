package com.carlos.gestorfinancas.dtos.nubank;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String description;
	private String category;
	private double amount;
	private double amount_without_iof;
	private Date time;
	private String title;
	
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

	public Transaction(String id, String description, String category, double amount, double amount_without_iof,
			Date time, String title) {
		super();
		this.id = id;
		this.description = description;
		this.category = category;
		this.amount = amount;
		this.amount_without_iof = amount_without_iof;
		this.time = time;
		this.title = title;
	}
	
	public boolean isCreditCardTransaction() {
		return getCategory().equalsIgnoreCase("transaction");
	}
	
	public static boolean isCreditCardTransaction(String category) {
		return category.equalsIgnoreCase("transaction");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
