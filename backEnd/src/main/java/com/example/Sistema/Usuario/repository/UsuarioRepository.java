package com.example.Sistema.Usuario.repository;


import com.example.Sistema.Usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Short> {
   Optional<Usuario> findByLogin(String login);

}
