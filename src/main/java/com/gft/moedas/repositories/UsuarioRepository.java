package com.gft.moedas.repositories;

import com.gft.moedas.entities.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByUsername(String firstName);
}
