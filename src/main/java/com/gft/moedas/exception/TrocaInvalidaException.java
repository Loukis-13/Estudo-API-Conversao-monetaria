package com.gft.moedas.exception;

import java.util.List;

public class TrocaInvalidaException extends MoedasException {
	public TrocaInvalidaException(List<String> message) {
		super(message);
	}

	public TrocaInvalidaException(String message) {
		super(List.of(message));
	}
}
