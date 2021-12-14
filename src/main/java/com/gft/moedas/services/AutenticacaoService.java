package com.gft.moedas.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gft.moedas.DTO.auth.AutenticacaoDTO;
import com.gft.moedas.DTO.auth.TokenDTO;
import com.gft.moedas.entities.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AutenticacaoService {
    @Value("${jwt.secret}") String secret;
    @Value("${jwt.expiration}") String expiration;
    @Value("${jwt.issuer}") String issuer;

    private final AuthenticationManager authManager;

    public AutenticacaoService(@Lazy AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

    public TokenDTO autenticar(AutenticacaoDTO authForm) throws AuthenticationException {
        Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(authForm.getUsername(), authForm.getPassword()));
        return new TokenDTO(gerarToken(authenticate));
    }

    private String gerarToken(Authentication authenticate) {
        Usuario usuario = (Usuario) authenticate.getPrincipal();
        Date dataExpiracao = new Date(new Date().getTime() + Long.parseLong(expiration));

        return JWT.create().withIssuer(issuer).withExpiresAt(dataExpiracao).withSubject(usuario.getId().toString()).sign(algorithm());
    }

    public boolean verificaToken(String token) {
        if (token == null) return false;

        try {
            JWT.require(algorithm()).withIssuer(issuer).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String retornarIdUsuario(String token) {
        String subject = JWT.require(algorithm()).withIssuer(issuer).build().verify(token).getSubject();
        return subject;
    }
}
