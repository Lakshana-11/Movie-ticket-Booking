package com.cts.moviebookingapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.moviebookingapp.response.ResponseHandler;

@ControllerAdvice
public class DuplicateMovieIdException extends Exception {
	
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(value = DuplicateMovieIdException.class)
	public ResponseEntity<Object> MovieAlreadyExists(DuplicateMovieIdException exception) {
		return ResponseHandler.generateResponse("Movie Already Exists", HttpStatus.CONFLICT, null);
	}
}
