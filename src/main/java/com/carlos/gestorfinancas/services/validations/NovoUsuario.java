package com.carlos.gestorfinancas.services.validations;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 04/08/2019
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = NovoUsuarioValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NovoUsuario {
	String message() default "Erro de validação";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}