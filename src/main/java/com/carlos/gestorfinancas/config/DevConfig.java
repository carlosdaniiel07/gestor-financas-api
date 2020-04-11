package com.carlos.gestorfinancas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.carlos.gestorfinancas.services.EmailService;
import com.carlos.gestorfinancas.services.FirebaseCloudMessagingService;
import com.carlos.gestorfinancas.services.MockEmailService;
import com.carlos.gestorfinancas.services.NotificacaoService;
import com.carlos.gestorfinancas.services.S3Service;
import com.carlos.gestorfinancas.services.StorageService;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
@Configuration
@Profile("dev")
public class DevConfig {
	
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
	
	@Bean
	public StorageService storageService() {
		return new S3Service();
	}
	
	@Bean
	public NotificacaoService notificacaoService() {
		return new FirebaseCloudMessagingService();
	}
}
