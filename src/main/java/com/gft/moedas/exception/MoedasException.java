package com.gft.moedas.exception;

public class MoedasException extends RuntimeException {
	private String message;

	public MoedasException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
