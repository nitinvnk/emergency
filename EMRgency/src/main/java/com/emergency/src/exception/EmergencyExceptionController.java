package com.emergency.src.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class EmergencyExceptionController {

	@ExceptionHandler
	public ResponseEntity<EmergencyErrorResponse> handleNotFoundException(UserNotFoundException ex,
			WebRequest request) {
		EmergencyErrorResponse exResponse = new EmergencyErrorResponse();
		exResponse.setStatus(HttpStatus.NOT_FOUND.value());
		exResponse.setMessage(ex.getMessage());
		exResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<EmergencyErrorResponse>(exResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<EmergencyErrorResponse> handleUserExistException(EmergencyException ex) {
		EmergencyErrorResponse exResponse = new EmergencyErrorResponse();
		exResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		exResponse.setMessage(ex.getMessage());
		exResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<EmergencyErrorResponse>(exResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<EmergencyErrorResponse> handleException(Exception ex) {
		EmergencyErrorResponse exResponse = new EmergencyErrorResponse();
		exResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		exResponse.setMessage(ex.getMessage());
		exResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<EmergencyErrorResponse>(exResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler
	public ResponseEntity<EmergencyErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		EmergencyErrorResponse exResponse = new EmergencyErrorResponse();
		exResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		exResponse.setMessage(ex.getMessage());
		exResponse.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<EmergencyErrorResponse>(exResponse, HttpStatus.BAD_REQUEST);
	}
}
