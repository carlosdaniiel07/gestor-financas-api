package com.carlos.gestorfinancas.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
@Service
public class S3Service {

	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${s3.bucket}")
	private String s3BucketName;
	
	/**
	 * Realiza o upload de um arquivo no S3
	 */
	public void uploadFile(String filePath) {
		try {
			File file = new File(filePath);
			
			if(file.exists()) {
				amazonS3.putObject(new PutObjectRequest(s3BucketName, "upload-" + filePath, file));
				
			} else {
				throw new OperacaoInvalidaException(String.format("O arquivo informado (%s) não existe", filePath));
			}
		} catch(AmazonClientException e) {
			e.printStackTrace();
		}
	}
}
