package com.carlos.gestorfinancas.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
@Entity
public class Anexo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private String extensao;
	private Long tamanho;
	private String urlS3;
	
	public Anexo() {
		
	}
	
	/**
	 * @param id
	 * @param nome
	 * @param extensao
	 * @param tamanho
	 * @param urlS3
	 */
	public Anexo(Long id, String nome, String extensao, Long tamanho, String urlS3) {
		super();
		this.id = id;
		this.nome = nome;
		this.extensao = extensao;
		this.tamanho = tamanho;
		this.urlS3 = urlS3;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the extensao
	 */
	public String getExtensao() {
		return extensao;
	}

	/**
	 * @param extensao the extensao to set
	 */
	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	/**
	 * @return the tamanho
	 */
	public Long getTamanho() {
		return tamanho;
	}

	/**
	 * @param tamanho the tamanho to set
	 */
	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	/**
	 * @return the urlS3
	 */
	public String getUrlS3() {
		return urlS3;
	}

	/**
	 * @param urlS3 the urlS3 to set
	 */
	public void setUrlS3(String urlS3) {
		this.urlS3 = urlS3;
	}
}
