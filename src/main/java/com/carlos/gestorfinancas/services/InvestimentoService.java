package com.carlos.gestorfinancas.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.entities.Investimento;
import com.carlos.gestorfinancas.entities.ItemInvestimento;
import com.carlos.gestorfinancas.entities.enums.TipoItemInvestimento;
import com.carlos.gestorfinancas.repositories.InvestimentoRepository;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

@Service
public class InvestimentoService {
	@Autowired
	private InvestimentoRepository repository;
	
	@Autowired
	private CorretoraService corretoraService;
	
	/**
	 * Grava um novo investimento junto com um item de aplicação inicial
	 * @param investimento
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Investimento insere(Investimento investimento) {
		ItemInvestimento itemAplicacao;
		
		investimento.setDataReinvestimento(null);
		investimento.setDataResgate(null);
		investimento.setValorAtual(investimento.getValorAplicado());
		investimento.setValorResgatado(0);
		
		// Gera item de aplicação inicial
		itemAplicacao = new ItemInvestimento(
				null, 
				TipoItemInvestimento.APLICACAO, 
				"Aplicação - " + investimento.getDescricao(), 
				investimento.getDataAplicacao(), 
				investimento.getValorAplicado(), 
				0, 
				0, 
				0, 
				0, 
				investimento
		);
		
		investimento.setItens(Arrays.asList(itemAplicacao));
		
		// Adiciona valor aplicado no saldo da corretora
		corretoraService.novaAplicacao(investimento.getCorretora(), investimento.getValorAplicado());
		
		return repository.save(investimento);
	}
	
	/**
	 * Registra um reinvestimento para um investimento já existente
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Investimento addReinvestimento(Investimento investimento, ItemInvestimento item) {
		List<ItemInvestimento> itensInvestimento = investimento.getItens();
		
		item.setTipo(TipoItemInvestimento.REINVESTIMENTO);
		item.setIof(0);
		item.setIr(0);
		item.setRendimento(0);
		
		itensInvestimento.add(item);
		
		investimento.setItens(itensInvestimento);
		investimento.setValorAplicado(investimento.getValorAplicado() + item.getValorReal());
		investimento.setValorAtual(investimento.getValorAtual() + item.getValorReal());
		
		if (investimento.getDataReinvestimento() == null || investimento.getDataReinvestimento().before(item.getData())) {
			investimento.setDataReinvestimento(item.getData());
		}
		
		// Adiciona valor aplicado no saldo da corretora
		corretoraService.novaAplicacao(investimento.getCorretora(), item.getValorReal());
		
		return repository.save(investimento);
	}
	
	/**
	 * Registra um resgate para um investimento já existente
	 * @param investimento
	 * @param item
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Investimento addResgate(Investimento investimento, ItemInvestimento item) {
		if (item.getValor() <= investimento.getValorAtual()) {
			List<ItemInvestimento> itensInvestimento = investimento.getItens();
			
			item.setTipo(TipoItemInvestimento.RESGATE);
			
			itensInvestimento.add(item);
			
			investimento.setItens(itensInvestimento);
			investimento.setValorAtual(investimento.getValorAtual() - item.getValor());
			investimento.setValorResgatado(item.getValor());
			
			if (investimento.getDataResgate() == null || investimento.getDataResgate().before(item.getData())) {
				investimento.setDataResgate(item.getData());
			}
			
			// Diminui o valor aplicado no saldo da corretora
			corretoraService.novoResgate(investimento.getCorretora(), item.getValor(), item.getRendimento());
			
			return repository.save(investimento);
		} else {
			throw new OperacaoInvalidaException("O valor a resgatar deve ser igual ou inferior ao valor aplicado");
		}
	}
}
