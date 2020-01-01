package com.carlos.gestorfinancas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.ModalidadeInvestimento;

@Repository
public interface ModalidadeInvestimentoRepository extends JpaRepository<ModalidadeInvestimento, Long> {
	List<ModalidadeInvestimento> findByAtivo(boolean ativo);
}
