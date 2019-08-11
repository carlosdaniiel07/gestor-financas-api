package com.carlos.gestorfinancas.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.carlos.gestorfinancas.services.exceptions.StorageException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 10/08/2019
 */
@Service
public class S3Service implements StorageService {

	@Autowired
	private AmazonS3 amazonS3;

	@Value("${s3.bucket}")
	private String s3BucketName;

	/**
	 * Realiza o upload de um arquivo no S3
	 * @throws IOException 
	 */
	@Override
	public void uploadFile(MultipartFile file) {
		try {
			ObjectMetadata metaData = new ObjectMetadata();
			
			metaData.setContentType(file.getContentType());
			amazonS3.putObject(s3BucketName, file.getOriginalFilename(), file.getInputStream(), metaData);
		} catch (IOException | AmazonServiceException ex) {
			throw new StorageException(ex.getMessage());
		}
	}
	
	/**
	 * Retorna a URL de um arquivo que está armazenado no S3
	 * @param fileName
	 * @return
	 */
	@Override
	public String getUrlByFile(String fileName) {
		return amazonS3.getUrl(s3BucketName, fileName).toString();
	}
}