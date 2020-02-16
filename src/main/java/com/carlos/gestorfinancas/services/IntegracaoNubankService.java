package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.dtos.nubank.Transaction;
import com.carlos.gestorfinancas.entities.IntegracaoNubank;
import com.carlos.gestorfinancas.repositories.IntegracaoNubankRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.utils.DateUtils;

@Service
public class IntegracaoNubankService {
	
	@Autowired
	private IntegracaoNubankRepository repository;
	
	/**
	 * Realiza a busca de uma transação a partir do seu id ('transactionId')
	 * @param transactionId
	 * @return
	 */
	public IntegracaoNubank findByTransactionId(String transactionId) {
		return repository.findById(transactionId).orElseThrow(() -> new ObjetoNaoEncontradoException("A transação " + transactionId + " ainda não foi integrada"));
	}
	
	/**
	 * Realiza a inserção de uma transação na tabela de controle de integração
	 * @param transactionId
	 * @return
	 */
	public IntegracaoNubank insert(Transaction transaction) {
		return repository.save(new IntegracaoNubank(transaction.getId(), 
													transaction.getDescription(), 
													transaction.getCategory(), 
													transaction.getAmount(), 
													transaction.getAmount_without_iof(), 
													transaction.getTime(), 
													transaction.getTitle(), 
													DateUtils.getDataAtual()));
	}
}
