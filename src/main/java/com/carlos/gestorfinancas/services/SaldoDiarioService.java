package com.carlos.gestorfinancas.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.SaldoDiario;
import com.carlos.gestorfinancas.repositories.SaldoDiarioRepository;

@Service
public class SaldoDiarioService {

	@Autowired
	private SaldoDiarioRepository repository;
	
	public void insere(Collection<SaldoDiario> colecaoSaldoDiario) {
		repository.saveAll(colecaoSaldoDiario);
	}
}
