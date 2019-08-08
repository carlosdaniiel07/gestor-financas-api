package com.carlos.gestorfinancas.services;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public interface EmailService {
	
	public void enviaEmail(String assunto, String destinatario, String templateName, String templateVar, Object data);

	
}
