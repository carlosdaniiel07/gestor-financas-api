package com.carlos.gestorfinancas.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.gestorfinancas.dtos.SubcategoriaDTO;
import com.carlos.gestorfinancas.repositories.SubcategoriaRepository;
import com.carlos.gestorfinancas.utils.ConstraintValidatorUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class NovaSubcategoriaValidator implements ConstraintValidator<NovaSubcategoria, SubcategoriaDTO> {	
	@Autowired
	private SubcategoriaRepository repository;
	
	@Override
	public boolean isValid(SubcategoriaDTO obj, ConstraintValidatorContext context) {
		if(!repository.findByNomeAndCategoriaIdAndAtivo(obj.getNome(), obj.getCategoria().getId(), true).isEmpty()) {
			String errorMessage = String.format("Já existe uma subcategoria com o nome %s pertencendo a categoria %s", obj.getNome(), obj.getCategoria().getNome());
			ConstraintValidatorUtils.addValidationError(errorMessage, "nome", context);
			
			return false;
		}
		
		return true;
	}
}
