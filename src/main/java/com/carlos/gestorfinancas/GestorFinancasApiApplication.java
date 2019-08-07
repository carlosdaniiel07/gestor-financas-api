package com.carlos.gestorfinancas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carlos.gestorfinancas.services.ContaService;

@SpringBootApplication
public class GestorFinancasApiApplication implements CommandLineRunner {
	@Autowired
	private ContaService contaService;
	
	public static void main(String[] args) {
		SpringApplication.run(GestorFinancasApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		contaService.ajustaSaldo(contaService.getAll().get(0));
	}
}
