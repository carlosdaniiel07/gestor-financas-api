package com.carlos.gestorfinancas.services;

import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.services.exceptions.StorageException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
public interface StorageService {
	
	/**
	 * Faz o upload de um arquivo no storage
	 * @param file
	 * @throws StorageException
	 */
	public void uploadFile(MultipartFile file, String keyName) throws StorageException;
	
	/**
	 * Retorna a URL do arquivo a por meio de seu nome
	 * @param fileName
	 * @return
	 */
	public String getUrlByFile(String fileName);
	
	/**
	 * Remove um arquivo do storage a partir doseu nome
	 */
	public void deleteByFileName(String fileName) throws StorageException;
}
