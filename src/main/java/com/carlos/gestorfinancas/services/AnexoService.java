package com.carlos.gestorfinancas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.entities.Anexo;
import com.carlos.gestorfinancas.repositories.AnexoRepository;
import com.carlos.gestorfinancas.services.exceptions.StorageException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 11/08/2019
 */
@Service
public class AnexoService {

	@Autowired
	private AnexoRepository repository;

	@Autowired
	private StorageService storageService;
	
	/**
	 * Faz o upload de um arquivo no storage e salva os seus dados no banco de dados
	 * @param file
	 * @return
	 */
	public Anexo insere(MultipartFile file) {
		try {
			// Faz o upload do arquivo no storage
			storageService.uploadFile(file);
			
			Anexo anexo = new Anexo();
			String fileName = file.getOriginalFilename();
			
			anexo.setNome(fileName);
			anexo.setExtensao(getExtensaoArquivo(fileName));
			anexo.setTamanho(file.getSize());
			anexo.setUrlS3(storageService.getUrlByFile(fileName));
			
			return repository.save(anexo);
		} catch (StorageException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}
	
	private String getExtensaoArquivo(String fileName) {
		if(fileName.contains(".")) {
			return fileName.substring(fileName.length() - 3);
		}
		
		return "";
	}
}
