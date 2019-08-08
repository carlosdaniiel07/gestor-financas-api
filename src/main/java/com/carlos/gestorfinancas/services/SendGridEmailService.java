package com.carlos.gestorfinancas.services;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public class SendGridEmailService extends AbstractEmailService {
	
	private final String sendGridApiKey = System.getenv("SENDGRID_API_KEY");
	private final String emailRemetente = System.getenv("SENDGRID_EMAIL");
	
	/*
	 * Envia um e-mail no formato HTML (text/html) usando a API do SendGrid
	 */
	@Override
	public void enviaEmail(String assunto, String destinatario, String templateName, String templateVar, Object data) {
		// Dados do e-mail a ser enviado..
		Email emailRemetente = new Email(this.emailRemetente);
		Email emailDestinatario = new Email(destinatario);
		Content emailContent = new Content("text/html", this.getHtmlFromTemplate(templateName, templateVar, data));
		
		Mail mail = new Mail(emailRemetente, "[Gestor de finanças] - " + assunto, emailDestinatario, emailContent);	
		SendGrid sendGridApi = new SendGrid(this.sendGridApiKey);
		Request httpRequest = new Request();
		
		try {
			// Prepara a requisição HTTP p/ API do SendGrid
			httpRequest.setMethod(Method.POST);
			httpRequest.setEndpoint("mail/send");
			httpRequest.setBody(mail.build());
			
			// Envia requisição p/ API
			sendGridApi.api(httpRequest);
			salvaCorpoEmail(emailContent.getValue());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
