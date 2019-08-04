package com.carlos.gestorfinancas.utils;

import javax.validation.ConstraintValidatorContext;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
public class ConstraintValidatorUtils {
	/*
	 * Adiciona um erro no contexto de validação do Spring
	 */
	public static void addValidationError(String error, String field, ConstraintValidatorContext context) {
		context.buildConstraintViolationWithTemplate(error).addPropertyNode(field).addConstraintViolation();
	}
}
