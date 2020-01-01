package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.ModalidadeInvestimento;
import com.carlos.gestorfinancas.repositories.ModalidadeInvestimentoRepository;

@Service
public class ModalidadeInvestimentoService {
	@Autowired
	private ModalidadeInvestimentoRepository repository;

	public List<ModalidadeInvestimento> getAll() {
		return repository.findByAtivo(true);
	}
}
