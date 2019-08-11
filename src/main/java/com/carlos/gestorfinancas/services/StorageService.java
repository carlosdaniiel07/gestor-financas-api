package com.carlos.gestorfinancas.services;

import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.services.exceptions.StorageException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
public interface StorageService {
	
	public void uploadFile(MultipartFile file) throws StorageException;
	
	public String getUrlByFile(String fileName);
}
