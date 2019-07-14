package com.carlos.gestorfinancas.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.carlos.gestorfinancas.entities.RequestError;
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
}
