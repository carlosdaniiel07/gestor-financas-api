package com.carlos.gestorfinancas.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 07/08/2019
 */
public class MockEmailService implements EmailService {
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
		
		// Loga o contéudo do e-mail em um arquivo HTML
		String tempPath = System.getenv("TEMP");
		String nomeArquivo = "MockEmail_" + DateUtils.getDataAtualAsFileName() + ".html";
		String dirArquivo = tempPath + "\\" + nomeArquivo;
		
		try {
			FileWriter fileWriter = new FileWriter(new File(dirArquivo));
			
			fileWriter.write(conteudo);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
