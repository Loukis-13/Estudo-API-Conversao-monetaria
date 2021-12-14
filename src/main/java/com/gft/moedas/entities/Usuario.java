package com.gft.moedas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gft.moedas.DTO.usuario.RegistroUsuarioDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(value = "users")
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    private String id;

    private String username;
    @JsonIgnore
    private String password;

    private List<Troca> trocas;

    private Date createdAt;

    public Usuario() {
    }

    public Usuario(RegistroUsuarioDTO dto) {
        username = dto.getNome();
        password = dto.getSenha();
        trocas = new ArrayList<>();
        createdAt = new Date();
    }

    public Usuario(String nome, String senha) {
        username = nome;
        password = senha;
        trocas = new ArrayList<>();
        createdAt = new Date();
    }

    public Usuario(String id, String username, String password, List<Troca> trocas, Date createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.trocas = trocas;
        this.createdAt = createdAt;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
