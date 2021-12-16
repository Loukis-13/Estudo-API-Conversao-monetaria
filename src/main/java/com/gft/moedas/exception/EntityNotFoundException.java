package com.gft.moedas.exception;

import java.util.List;

public class EntityNotFoundException extends MoedasException {
	public EntityNotFoundException(List<String> message) {
		super(message);
	}

	public EntityNotFoundException(String message) {
		super(List.of(message));
	}
}
