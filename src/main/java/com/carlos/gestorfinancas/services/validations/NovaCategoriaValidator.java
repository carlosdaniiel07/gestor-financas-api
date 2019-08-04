package com.carlos.gestorfinancas.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.gestorfinancas.dtos.CategoriaDTO;
import com.carlos.gestorfinancas.repositories.CategoriaRepository;
import com.carlos.gestorfinancas.utils.ConstraintValidatorUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class NovaCategoriaValidator implements ConstraintValidator<NovaCategoria, CategoriaDTO> {	
	@Autowired
	private CategoriaRepository repository;
	
	@Override
	public boolean isValid(CategoriaDTO obj, ConstraintValidatorContext context) {
		if(!repository.findByNomeAndTipoAndAtivo(obj.getNome(), obj.getTipo(), true).isEmpty()) {
			String errorMessage = String.format("Já existe uma categoria do tipo %s com o nome %s", (obj.getTipo() == 'C' ? "crédito" : "débito"), obj.getNome());
			ConstraintValidatorUtils.addValidationError(errorMessage, "nome", context);
			return false;
		}
		
		return true;
	}
}
