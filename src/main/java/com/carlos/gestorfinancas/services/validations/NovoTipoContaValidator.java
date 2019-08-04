package com.carlos.gestorfinancas.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.gestorfinancas.dtos.TipoContaDTO;
import com.carlos.gestorfinancas.repositories.TipoContaRepository;
import com.carlos.gestorfinancas.utils.ConstraintValidatorUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class NovoTipoContaValidator implements ConstraintValidator<NovoTipoConta, TipoContaDTO> {	
	@Autowired
	private TipoContaRepository repository;
	
	@Override
	public boolean isValid(TipoContaDTO obj, ConstraintValidatorContext context) {
		if(!repository.findByNomeAndAtivo(obj.getNome(), true).isEmpty()) {
			ConstraintValidatorUtils.addValidationError(String.format("Já existe um tipo de conta com o nome %s", obj.getNome()), "nome", context);
			return false;
		}
		
		return true;
	}
}
