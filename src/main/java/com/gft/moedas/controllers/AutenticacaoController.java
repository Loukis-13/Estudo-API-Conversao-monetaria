package com.gft.moedas.controllers;

import com.gft.moedas.DTO.auth.AutenticacaoDTO;
import com.gft.moedas.DTO.auth.TokenDTO;
import com.gft.moedas.entities.Usuario;
import com.gft.moedas.exception.AlreadyExistentUserException;
import com.gft.moedas.services.AutenticacaoService;
import com.gft.moedas.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(name = "Auth", description = "autenticação de usuário")
public class AutenticacaoController {
    @Autowired
    AutenticacaoService autenticacaoService;

    @Autowired
    UsuarioService usuarioService;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @PostMapping
    @Operation(summary = "Autenticar usuário")
    public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO authform) {
        try {
            return ResponseEntity.ok(autenticacaoService.autenticar(authform));
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("criar")
    @Operation(summary = "Criar novo usuário")
    public ResponseEntity<TokenDTO> criarConta(@RequestBody AutenticacaoDTO authform) {
        Usuario usuario = usuarioService.buscarUsuarioPorNome(authform.getUsername());

        if (usuario != null) {
            throw new AlreadyExistentUserException("Nome de usuário já em uso");
        }

        usuarioService.salvarUsuario(
                new Usuario(authform.getUsername(), PASSWORD_ENCODER.encode(authform.getPassword()))
        );

        return autenticar(authform);
    }
}