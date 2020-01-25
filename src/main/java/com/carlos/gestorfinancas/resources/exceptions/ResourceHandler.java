package com.carlos.gestorfinancas.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.carlos.gestorfinancas.services.exceptions.AuthenticationException;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@RestControllerAdvice
public class ResourceHandler {

	@ExceptionHandler(OperacaoInvalidaException.class)
	public ResponseEntity<RequestError> operacaoInvalidaHandler(OperacaoInvalidaException ex) {
		RequestError requestError = new RequestError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return ResponseEntity.badRequest().body(requestError);
	}
	
	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<RequestError> objetoNaoEncontradoHandler(ObjetoNaoEncontradoException ex) {
		RequestError requestError = new RequestError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return ResponseEntity.status(requestError.getHttpStatus()).body(requestError);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RequestError> validacaoHandler(MethodArgumentNotValidException ex) {
		ValidationError validationError = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de validação");
		
		// Obtem os erros
		for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			validationError.addError(fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(validationError.getHttpStatus()).body(validationError);
	}
	
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	public ResponseEntity<RequestError> acessoNegadoHandler(org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
		RequestError requestError = new RequestError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "O seu perfil não tem acesso a este recurso");		
		return ResponseEntity.status(requestError.getHttpStatus()).body(requestError);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<RequestError> usuarioNaoLogado(AuthenticationException ex) {
		RequestError requestError = new RequestError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(requestError);
	}
}
