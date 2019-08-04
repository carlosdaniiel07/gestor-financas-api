package com.carlos.gestorfinancas.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.gestorfinancas.dtos.ContaDTO;
import com.carlos.gestorfinancas.repositories.ContaRepository;
import com.carlos.gestorfinancas.utils.ConstraintValidatorUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class NovaContaValidator implements ConstraintValidator<NovaConta, ContaDTO> {	
	@Autowired
	private ContaRepository repository;
	
	@Override
	public boolean isValid(ContaDTO obj, ConstraintValidatorContext context) {
		if(!repository.findByNomeAndAtivo(obj.getNome(), true).isEmpty()) {
			ConstraintValidatorUtils.addValidationError(String.format("Já existe uma conta com o nome %s", obj.getNome()), "nome", context);
			return false;
		}
		
		return true;
	}
}
