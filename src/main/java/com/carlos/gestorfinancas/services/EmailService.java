package com.carlos.gestorfinancas.services;

import java.util.Collection;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public interface EmailService {
	public void enviaEmail(String assunto, Collection<String> destinatarios, String templateName, String templateVar, Object data);
	
	public Collection<String> getEmailsFromParam();
}
