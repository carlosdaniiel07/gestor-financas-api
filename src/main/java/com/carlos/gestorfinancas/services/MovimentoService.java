package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.entities.CartaoCredito;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.repositories.MovimentoRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class MovimentoService {
	@Autowired
	private MovimentoRepository repository;
	
	@Autowired
	private ContaService contaService;
	
	private final int dadosPorPagina = 30;
	private final String modulo = "MOVTO";
	
	public List<Movimento> getAll() {
		return repository.findAll();
	}
	
	public List<Movimento> getAll(int pagina) {
		return repository.findAll(PageRequest.of(pagina, dadosPorPagina)).getContent();
	}
	
	public List<Movimento> getByConta(Long contaId){
		return repository.findByContaId(contaId);
	}
	
	public List<Movimento> getByConta(Long contaId, int pagina){
		return repository.findByContaId(contaId, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public Movimento getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Esse movimento não foi encontrado."));
	}
	
	/**
	 * Gera um movimento bancário
	 * @param movimento => O movimento a ser gerado
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Movimento insere(Movimento movimento) {
		Movimento movimentoGerado = null;
		
		movimento.setDataInclusao(DateUtils.getDataAtual());
		movimento.setOrigem(movimento.getOrigem() == null ? modulo : movimento.getOrigem());
		
		if(movimento.hasCartaoCredito()) {
			movimento.setStatus(StatusMovimento.EFETIVADO);
			
			if(movimento.getTipo() == 'D') {
				Fatura fatura = movimento.getFatura();
				CartaoCredito cartao = fatura.getCartao();
				
				if(fatura.getStatus() != StatusFatura.NAO_FECHADA) {
					if(movimento.getValorTotal() < cartao.getLimite()) {
						if(cartao.getLimiteRestante() >= movimento.getValorTotal()) {
							// Salva o movimento no banco de dados
							movimentoGerado = repository.save(movimento);
						} else {
							throw new OperacaoInvalidaException("O cartão de crédito não tem saldo disponível.");
						}
					} else {
						throw new OperacaoInvalidaException(String.format("O valor do movimento está acima do limite do cartão de crédito (%f)", cartao.getLimite()));
					}
				} else {
					throw new OperacaoInvalidaException("A fatura já está fechada. É necessário abrir-lá novamente.");
				}
			} else {
				throw new OperacaoInvalidaException("Cartões de crédito só podem ser utilizados em despesas.");
			}
		} else {
			if(movimento.isEfetivado() && movimento.isFuturo(DateUtils.getDataAtual())) {
				movimento.setStatus(StatusMovimento.AGENDADO);
			}
			
			// Salva o movimento no banco de dados
			movimentoGerado = repository.save(movimento);
			
			// Ajusta o saldo da conta, se necessário..
			if(movimento.isEfetivado()) {
				contaService.ajustaSaldo(movimento.getConta());
			}
		}
		
		return movimentoGerado;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void efetiva(Long id) {
		Movimento obj = getById(id);
		
		if(!obj.isEfetivado()) {
			obj.setStatus(StatusMovimento.EFETIVADO);
			
			repository.save(obj);
			
			// Ajusta saldo da conta
			contaService.ajustaSaldo(obj.getConta());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Long id) {
		Movimento obj = getById(id);
		
		if(obj.getOrigem().equals(modulo)) {
			repository.delete(obj);
			
			// Ajusta saldo da conta, se necessário..
			if(obj.isEfetivado()) {
				contaService.ajustaSaldo(obj.getConta());
			}
		} else {
			throw new OperacaoInvalidaException("Este movimento foi gerado por outra rotina, portanto não pode ser alterado.");
		}
	}
	
	/*
	 * Retorna a soma de todos os movimentos de crédito de uma conta específica
	 */
	public double getTotalCreditoByConta(Conta conta) {
		return repository.getTotalCreditoByConta(conta.getId()).orElse(0D);
	}

	/*
	 * Retorna a soma de todos os movimentos de débito de uma conta específica (não considera movimentos vinculados a cartão de crédito)
	 */
	public double getTotalDebitoByConta(Conta conta) {
		return repository.getTotalDebitoByConta(conta.getId()).orElse(0D);
	}
}
