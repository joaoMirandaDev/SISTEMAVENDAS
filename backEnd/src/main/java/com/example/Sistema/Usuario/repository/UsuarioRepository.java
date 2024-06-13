package com.example.Sistema.Usuario.repository;


import com.example.Sistema.Usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Short> {
   Usuario findByLogin(String login);

}
