package com.carlos.gestorfinancas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.LogTask;

@Repository
public interface LogTaskRepository extends JpaRepository<LogTask, Long> {
	
}
