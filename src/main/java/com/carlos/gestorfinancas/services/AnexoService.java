package com.carlos.gestorfinancas.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.entities.Anexo;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.repositories.AnexoRepository;
import com.carlos.gestorfinancas.services.exceptions.StorageException;
import com.carlos.gestorfinancas.utils.StringUtils;

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
	public Anexo insere(Movimento movimento, MultipartFile file) {
		try {
			String fileName = StringUtils.generateRandomString(20) + "_" + file.getOriginalFilename();
			
			// Faz o upload do arquivo no storage
			storageService.uploadFile(file, fileName);
			
			Anexo anexo = new Anexo();
			
			anexo.setNome(fileName);
			anexo.setExtensao(getExtensaoArquivo(fileName));
			anexo.setTamanho(file.getSize());
			anexo.setUrlS3(storageService.getUrlByFile(fileName));
			anexo.setMovimento(movimento);
			
			return repository.save(anexo);
		} catch (StorageException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Remove um anexo
	 */
	public void remove(Anexo anexo) {
		try {
			storageService.deleteByFileName(anexo.getNome());
			repository.delete(anexo);
		} catch (StorageException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Remove uma coleção de anexos
	 * @param anexos
	 */
	public void remove(Collection<Anexo> anexos) {
		try {
			anexos.forEach(a -> storageService.deleteByFileName(a.getNome()));
			repository.deleteAll(anexos);
		} catch (StorageException ex) {
			ex.printStackTrace();
		}
	}
	
	private String getExtensaoArquivo(String fileName) {
		if(fileName.contains(".")) {
			return fileName.substring(fileName.length() - 3);
		}
		
		return "";
	}
}
