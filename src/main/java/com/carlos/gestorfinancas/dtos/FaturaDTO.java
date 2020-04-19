package com.carlos.gestorfinancas.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.carlos.gestorfinancas.entities.CartaoCredito;
import com.carlos.gestorfinancas.entities.Fatura;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/09/2019
 */
public class FaturaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "É obrigatório informar o mês e ano de referência")
	@Length(min = 8, max = 8, message = "A referência precisa ter exatamente 8 caracteres")
	private String referencia;
	
	@NotNull(message = "É obrigatório informar o cartão de crédito desta fatura")
	private CartaoCredito cartao;

	public FaturaDTO() {
		super();
	}
	
	/**
	 * @param referencia
	 * @param cartao
	 */
	public FaturaDTO(@NotEmpty(message = "É obrigatório informar o mês e ano de referência") String referencia,
			@NotNull(message = "É obrigatório informar o cartão de crédito desta fatura") CartaoCredito cartao) {
		super();
		this.referencia = referencia;
		this.cartao = cartao;
	}
	
	public Fatura toFatura() {
		return new Fatura(null, getReferencia(), null, null, null, 0, 0, null, getCartao());
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	/**
	 * @return the cartao
	 */
	public CartaoCredito getCartao() {
		return cartao;
	}

	/**
	 * @param cartao the cartao to set
	 */
	public void setCartao(CartaoCredito cartao) {
		this.cartao = cartao;
	}
}
