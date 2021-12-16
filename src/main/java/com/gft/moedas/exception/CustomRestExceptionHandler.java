package com.gft.moedas.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gft.moedas.DTO.ApiErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler( { MoedasException.class } )
	public ResponseEntity<ApiErrorDTO> handleLojaException(MoedasException ex, WebRequest request){
		String error = "Erro no sistema";
		
		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMenssagem(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@ExceptionHandler( { EntityNotFoundException.class } )
	public ResponseEntity<ApiErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
		String error = "Recurso não encontrado";
		
		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMenssagem(), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler( { AlreadyExistentUserException.class } )
	public ResponseEntity<ApiErrorDTO> handleAlreadyExistentUserException(AlreadyExistentUserException ex, WebRequest request){
		String error = "Nome de usuário já em uso";

		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMenssagem(), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler( { UsernameNotFoundException.class } )
	public ResponseEntity<ApiErrorDTO> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request){
		String error = "Usuário não encontrado";

		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMessage(), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler( { InternalAuthenticationServiceException.class } )
	public ResponseEntity<ApiErrorDTO> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex, WebRequest request){
		String error = "Nome ou senha incorretos";

		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMessage(), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler( { TrocaInvalidaException.class } )
	public ResponseEntity<ApiErrorDTO> handleTrocaInvalidaException(TrocaInvalidaException ex, WebRequest request){
		String error = "Troca invalida";

		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMenssagem(), HttpStatus.BAD_REQUEST);

		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler( { JsonProcessingException.class } )
	public ResponseEntity<ApiErrorDTO> handleJsonProcessingException(JsonProcessingException ex, WebRequest request){
		String error = "Falha ao pegar valores das moedas";

		ApiErrorDTO apiError = new ApiErrorDTO(error, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}
