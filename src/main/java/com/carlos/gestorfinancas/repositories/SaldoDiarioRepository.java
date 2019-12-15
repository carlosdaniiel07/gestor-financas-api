package com.carlos.gestorfinancas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.SaldoDiario;

@Repository
public interface SaldoDiarioRepository extends JpaRepository<SaldoDiario, Long> {
	List<SaldoDiario> findByNomeContaAndData(String nomeConta, Date data);
}
