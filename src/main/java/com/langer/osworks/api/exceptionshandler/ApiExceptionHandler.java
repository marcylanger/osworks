package com.langer.osworks.api.exceptionshandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.langer.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.langer.osworks.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocio(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		var status = HttpStatus.NOT_FOUND;
		
		var erro = new Error();
		erro.setStatus(status.value());
		erro.setTitulo(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
		erro.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		
		var status = HttpStatus.BAD_REQUEST;
		
		var erro = new Error();
		erro.setStatus(status.value());
		erro.setTitulo(messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
		erro.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, erro, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var erro = new Error();
		erro.setStatus(status.value());
		erro.setTitulo("Um ou mais campos está inválidos. Faça o preenchimento correto e tente novamente.");
		erro.setDataHora(OffsetDateTime.now());
		
		var campos = new ArrayList<Error.Campo>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Error.Campo(nome, mensagem));
			
			erro.setCampos(campos);
		}
		
		return super.handleExceptionInternal(ex, erro, headers, status, request);
	}
}
