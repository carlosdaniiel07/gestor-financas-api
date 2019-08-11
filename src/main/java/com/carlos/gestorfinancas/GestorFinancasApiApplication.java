package com.carlos.gestorfinancas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carlos.gestorfinancas.services.S3Service;

@SpringBootApplication
public class GestorFinancasApiApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(GestorFinancasApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\carlo\\OneDrive\\Área de Trabalho\\nomes.txt");
	}
}
