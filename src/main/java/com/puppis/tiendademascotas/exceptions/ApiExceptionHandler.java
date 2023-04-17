package com.puppis.tiendademascotas.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@ControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

	//esta anotacion indica la o las expceciones java que va a manejar
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> notFoundException(Exception excepcion){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(excepcion.getMessage());
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> BadRequestException(Exception excepcion){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excepcion.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exception(Exception excepcion){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(excepcion.getMessage());
	}
	

	
	
	
	
	//ERROR "HttpMediaTypeNotAcceptableException: Could not find acceptable representation", es cuando el cliente solicita un formato de respuesta que el servidor no puede proporcionar
	/*
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public MensajeError notFoundException(Exception excepcion){
		return new MensajeError(excepcion, HttpStatus.NOT_FOUND.value());
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MensajeError badRequestdException(Exception excepcion){
		return new MensajeError(excepcion, HttpStatus.BAD_REQUEST.value());
	}
	
	  @ExceptionHandler(Exception.class)
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  @ResponseBody
	  public MensajeError exception(Exception excepcion) { 
		  excepcion.printStackTrace();
	        return new MensajeError(excepcion, HttpStatus.INTERNAL_SERVER_ERROR.value());
	    }
	  */

}
