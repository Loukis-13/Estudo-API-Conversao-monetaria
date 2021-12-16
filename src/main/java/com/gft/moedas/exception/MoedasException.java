package com.gft.moedas.exception;

import java.util.List;

public class MoedasException extends RuntimeException {
	private List<String> menssagem;

	public MoedasException(List<String> menssagem) {
		super(menssagem.toString());
		this.menssagem = menssagem;
	}

	public List<String> getMenssagem() {
		return menssagem;
	}

	public void setMenssagem(List<String> menssagem) {
		this.menssagem = menssagem;
	}
}
