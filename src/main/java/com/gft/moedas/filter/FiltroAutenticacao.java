package com.gft.moedas.filter;

import com.gft.moedas.services.AutenticacaoService;
import com.gft.moedas.entities.Usuario;
import com.gft.moedas.services.UsuarioService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FiltroAutenticacao extends OncePerRequestFilter {
    private AutenticacaoService autenticacaoService;
    private UsuarioService usuarioService;

    public FiltroAutenticacao(AutenticacaoService autenticacaoService, UsuarioService usuarioService) {
        this.autenticacaoService = autenticacaoService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = "";
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }

        if (autenticacaoService.verificaToken(token)) {
            String idUsuario = autenticacaoService.retornarIdUsuario(token);
            Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
        }

        filterChain.doFilter(request, response);
    }
}

