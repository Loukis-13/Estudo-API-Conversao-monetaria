package com.gft.moedas.controllers;

import com.gft.moedas.DTO.auth.AutenticacaoDTO;
import com.gft.moedas.DTO.usuario.ConsultaUsuarioDTO;
import com.gft.moedas.DTO.usuario.TrocaSenha;
import com.gft.moedas.entities.Usuario;
import com.gft.moedas.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usuario")
@Tag(name = "Usuário", description = "informações sobre usuários")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    @DeleteMapping
    @Operation(summary = "Excluir usuário")
    public ResponseEntity excluirUsuario(@RequestBody AutenticacaoDTO authform, Authentication auth) {
        if (authform.getUsername().equals(auth.getName()) && encoder.matches(authform.getPassword(), ((Usuario)auth.getPrincipal()).getPassword())) {
            Usuario usuario = usuarioService.buscarUsuarioPorNome(auth.getName());
            usuarioService.excluirUsuario(usuario);
            auth.setAuthenticated(false);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    @Operation(summary = "Trocar senha do usuário")
    public ResponseEntity<ConsultaUsuarioDTO> trocarSenhaUsuario(@RequestBody TrocaSenha trocaSenha, Authentication authentication) {
        if (trocaSenha.senha1.equals(trocaSenha.senha2)) {
            Usuario usuario = usuarioService.buscarUsuarioPorNome(authentication.getName());
            usuario.setPassword(encoder.encode(trocaSenha.senha1));
            usuarioService.salvarUsuario(usuario);

            authentication.setAuthenticated(false);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
