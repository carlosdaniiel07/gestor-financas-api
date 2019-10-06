package com.carlos.gestorfinancas.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${param.gera-log-email}")
	private boolean paramGeraLogEmail;
	
	@Value("${param.emails-para-notificacoes}")
	private String paramEmailsParaNotificacoes;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	/*
	 * Salva o corpo do e-mail enviado em um arquivo HTML 
	 */
	protected void salvaCorpoEmail(String htmlContent) {
		if(paramGeraLogEmail) {
			String tempPath = System.getenv("TEMP");
			
			if(tempPath != null) {
				String nomeArquivo = "EmailLog_" + DateUtils.getDataAtualAsFileName() + ".html";
				String dirArquivo = tempPath + "\\" + nomeArquivo;

				try {
					FileWriter fileWriter = new FileWriter(new File(dirArquivo));
					
					fileWriter.write(htmlContent);
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * Retorna o código HTML de um e-mail com base em seu template
	 */
	protected String getHtmlFromTemplate(String templateName, String varName, Object data) {
		Context template = new Context();
		
		template.setVariable(varName, data);
		
		return templateEngine.process("email/" + templateName, template);
	}
	
	/**
	 * Obtém em formato de array, os endereços de e-mail armazenados no parâmetro param.emails-para-notificacoes
	 * @return Uma coleção de e-mails
	 */
	@Override
	public Collection<String> getEmailsFromParam() {
		Set<String> emails = new HashSet<String>();
		char separator = ',';
		String[] values = paramEmailsParaNotificacoes
								.replace(';', separator)
								.replace('|', separator)
								.replace('/', separator).split(String.valueOf(separator));
		
		for(String email : values) {
			emails.add(email);
		}
		
		return emails;
	}
}
