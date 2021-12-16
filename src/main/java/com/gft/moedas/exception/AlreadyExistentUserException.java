package com.gft.moedas.exception;

import java.util.List;

public class AlreadyExistentUserException extends MoedasException {
	public AlreadyExistentUserException(List<String> message) {
		super(message);
	}

	public AlreadyExistentUserException(String message) {
		super(List.of(message));
	}
}
