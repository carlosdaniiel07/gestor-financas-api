package com.carlos.gestorfinancas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.carlos.gestorfinancas.services.NotificacaoService;

@SpringBootApplication
public class GestorFinancasApiApplication extends SpringBootServletInitializer implements CommandLineRunner {
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	public static void main(String[] args) {
		SpringApplication.run(GestorFinancasApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	}
}
