package com.gft.moedas.DTO.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private String nome;
    private String senha;

    public RegistroUsuarioDTO() {
    }

    public RegistroUsuarioDTO(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
}
