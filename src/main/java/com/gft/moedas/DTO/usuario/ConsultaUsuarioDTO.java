package com.gft.moedas.DTO.usuario;

import com.gft.moedas.entities.Troca;
import com.gft.moedas.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultaUsuarioDTO {
    private String username;
    private List<Troca> trocas;

    public ConsultaUsuarioDTO() {
    }

    public ConsultaUsuarioDTO(String username, List<Troca> trocas) {
        this.username = username;
        this.trocas = trocas;
    }

    public ConsultaUsuarioDTO(Usuario usuario) {
        username = usuario.getUsername();
        trocas = usuario.getTrocas();
    }
}
