package com.emergency.src.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7625592084761056508L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
