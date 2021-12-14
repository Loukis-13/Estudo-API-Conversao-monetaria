package com.gft.moedas.controllers;

import com.gft.moedas.DTO.usuario.ConsultaUsuarioDTO;
import com.gft.moedas.entities.Usuario;
import com.gft.moedas.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usuario")
@Tag(name = "Usuário", description = "informações sobre usuários")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("todos")
    @Operation(summary = "Ver todos os usuários")
    public ResponseEntity<List<ConsultaUsuarioDTO>> buscarUsuario() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(u -> new ConsultaUsuarioDTO(u)).collect(Collectors.toList()));
    }

    @GetMapping
    @Operation(summary = "Ver trocas do usuário")
    public ResponseEntity<ConsultaUsuarioDTO> buscarUsuario(Authentication authentication) {
        Usuario usuario = usuarioService.buscarUsuarioPorNome(authentication.getName());
        return ResponseEntity.ok(new ConsultaUsuarioDTO(usuario));
    }
}
