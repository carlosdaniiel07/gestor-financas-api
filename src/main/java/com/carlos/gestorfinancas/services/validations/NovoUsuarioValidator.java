package com.carlos.gestorfinancas.services.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlos.gestorfinancas.dtos.UsuarioDTO;
import com.carlos.gestorfinancas.repositories.UsuarioRepository;
import com.carlos.gestorfinancas.utils.ConstraintValidatorUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class NovoUsuarioValidator implements ConstraintValidator<NovoUsuario, UsuarioDTO> {	
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public boolean isValid(UsuarioDTO obj, ConstraintValidatorContext context) {
		if(!repository.findByEmailAndAtivo(obj.getEmail(), true).isEmpty()) {
			ConstraintValidatorUtils.addValidationError(String.format("Já existe um usuário com este e-mail (%s).", obj.getEmail()), "email", context);
			return false;
		}
		
		if(!repository.findByLoginAndAtivo(obj.getLogin(), true).isEmpty()) {
			ConstraintValidatorUtils.addValidationError(String.format("Já existe um usuário com este login (%s).", obj.getLogin()), "login", context);
			return false;
		}
		
		return true;
	}
}
