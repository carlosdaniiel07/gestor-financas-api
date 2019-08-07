package com.carlos.gestorfinancas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public class MockEmailService extends AbstractEmailService {
	private final static Logger log = LoggerFactory.getLogger(MockEmailService.class);
	private final String emailRemetente = "noreply@gestorfinancas.com";
	
	/*
	 * Envia um mock e-mail (apenas loga dados do e-mail no console)
	 */
	@Override
	public void enviaEmail(String assunto, String destinatario, String conteudo) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("\n");
		stringBuilder.append("----------------------------------\n");
		stringBuilder.append("Remetente: " + emailRemetente + "\n");
		stringBuilder.append("Destinatário: " + destinatario + "\n");
		stringBuilder.append("Assunto: " + assunto + "\n");
		stringBuilder.append("Conteúdo: " + conteudo + "\n");
		stringBuilder.append("----------------------------------\n");

		log.info(stringBuilder.toString());
		salvaCorpoEmail(conteudo);
	}
}
