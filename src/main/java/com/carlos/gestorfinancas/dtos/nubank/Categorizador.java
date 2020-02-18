package com.carlos.gestorfinancas.dtos.nubank;

import java.util.HashSet;
import java.util.Set;

public class Categorizador {
	
	private Set<String> pattern = new HashSet<String>();
	private String title;
	private String category;
	private String sucategory;
	
	public Categorizador() {
		
	}

	public Categorizador(Set<String> pattern, String title, String category, String sucategory) {
		super();
		this.pattern = pattern;
		this.title = title;
		this.category = category;
		this.sucategory = sucategory;
	}

	public Set<String> getPattern() {
		return pattern;
	}

	public void setPattern(Set<String> pattern) {
		this.pattern = pattern;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSucategory() {
		return sucategory;
	}

	public void setSucategory(String sucategory) {
		this.sucategory = sucategory;
	}
}
