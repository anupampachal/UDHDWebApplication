package com.scsinfinity.udhd.exceptions;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionTranslator extends ResponseEntityExceptionHandler {


	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException en, NativeWebRequest request){
		log.error("handleIllegalStateException", en);

		ErrorResponse response = ErrorResponse.builder().message(en.getMessage()).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.ENTITY_NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException en,NativeWebRequest request){
		log.error("handleDataIntegrityViolationException", en);
		String error="Entity constraint violation";
		if(en.getCause()!=null && en.getCause().getCause()!=null && en.getCause().getCause().getMessage()!=null && en.getCause().getCause().getMessage().contains("Unique index or primary key violation:")){
			error= "Duplicate value";
		}

		ErrorResponse response = ErrorResponse.builder().message(error).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.ENTITY_NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException en,
			NativeWebRequest request) {
		log.error("handleEntityNotFoundException", en);
		ErrorResponse response = ErrorResponse.builder().message(en.getMessage()).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.ENTITY_NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}


	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException en,
			NativeWebRequest request) {
		ErrorResponse response = ErrorResponse.builder().message(en.getMessage()).status(HttpStatus.UNAUTHORIZED)
				.statusCode(HttpStatus.UNAUTHORIZED.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.ENTITY_NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException en,
			NativeWebRequest request) {
		ErrorResponse response = ErrorResponse.builder().message(en.getMessage()).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.BAD_CREDENTIALS_EXCEPTION).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(BadRequestAlertException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestAlertException(BadRequestAlertException en,
			NativeWebRequest request) {
		ErrorResponse response = ErrorResponse.builder().message(en.getMessage()).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.withKeyAndValues(en.getParameters()).uri(en.getType()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestAlertException(StorageException en, NativeWebRequest request) {
		ErrorResponse response = ErrorResponse.builder().message(en.getMessage()).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.STORAGE_EXCEPTION_TYPE).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception en, NativeWebRequest request) {
		ErrorResponse response = ErrorResponse.builder().message("OTHER_EXCEPTION_OCCURED").status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now())
				.uri(ErrorConstants.OTHER_EXCEPTION_TYPE).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		BindingResult result = ex.getBindingResult();
		List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
				.map(f -> new FieldErrorVM(f.getObjectName(), f.getField(), f.getCode(), f.getDefaultMessage()))
				.collect(Collectors.toList());

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("fieldErrors", fieldErrors);

		ErrorResponse response = ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST)
				.statusCode(HttpStatus.BAD_REQUEST.value()).timestamp(LocalDateTime.now()).withKeyAndValues(body)
				.uri(ErrorConstants.ENTITY_NOT_FOUND).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

	}
}
