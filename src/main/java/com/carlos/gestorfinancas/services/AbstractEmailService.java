package com.carlos.gestorfinancas.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${param.gera-log-email}")
	private boolean paramGeraLogEmail;
	
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
}
