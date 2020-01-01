package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.ItemInvestimento;
import com.carlos.gestorfinancas.entities.enums.TipoItemInvestimento;
import com.carlos.gestorfinancas.repositories.ItemInvestimentoRepository;

@Service
public class ItemInvestimentoService {
	@Autowired
	private ItemInvestimentoRepository repository;

	public ItemInvestimento insere(ItemInvestimento item) {
		if (item.getTipo() == TipoItemInvestimento.APLICACAO || item.getTipo() == TipoItemInvestimento.REINVESTIMENTO) {
			item.setIof(0);
			item.setIr(0);
			item.setOutrasTaxas(0);
			item.setRendimento(0);
		} else {
			// Nenhuma tratativa específica é feita quando o item é um 'RESGATE
		}
		
		return repository.save(item);
	}
}
