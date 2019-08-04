package com.carlos.gestorfinancas.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.gestorfinancas.dtos.BeneficiarioDTO;
import com.carlos.gestorfinancas.repositories.BeneficiarioRepository;
import com.carlos.gestorfinancas.utils.ConstraintValidatorUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class NovoBeneficiarioValidator implements ConstraintValidator<NovoBeneficiario, BeneficiarioDTO> {	
	@Autowired
	private BeneficiarioRepository repository;
	
	@Override
	public boolean isValid(BeneficiarioDTO obj, ConstraintValidatorContext context) {
		if(!repository.findByNomeAndAtivo(obj.getNome(), true).isEmpty()) {
			ConstraintValidatorUtils.addValidationError(String.format("Já existe um beneficiário cadastrado com o nome %s", obj.getNome()), "nome", context);
			return false;
		}
		
		return true;
	}
}
