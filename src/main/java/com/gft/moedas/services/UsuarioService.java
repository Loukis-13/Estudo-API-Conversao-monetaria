package com.gft.moedas.services;

import com.gft.moedas.entities.Usuario;
import com.gft.moedas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorNome(username);
    }

    public Usuario buscarUsuarioPorNome(String nome) {
        return usuarioRepository.findByUsername(nome);
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
}
