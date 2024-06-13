package com.example.Sistema.Usuario.controller;


import com.example.Sistema.Autenticacao.DTO.CredenciaisDTO;
import com.example.Sistema.Autenticacao.DTO.TokenDTO;
import com.example.Sistema.Usuario.DTO.UsuarioDTO;
import com.example.Sistema.Usuario.model.Usuario;
import com.example.Sistema.Autenticacao.config.securityJwt.JwtService;
import com.example.Sistema.Usuario.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final JwtService jwtService;

    public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "FindById", description = "Resgastar os usuarios por ID", tags = "Usuario")
    public Optional<Usuario> findById(@PathVariable Short id) {
        return usuarioService.findById(id);
    }

    @GetMapping("/findByLogin/{login}")
    @Operation(summary = "FindByLogin", description = "Resgastar os usuarios por login", tags = "Usuario")
    public UsuarioDTO findByLogin(@PathVariable String login) {
        return usuarioService.findByLogin(login);
    }

    @PostMapping("/auth")
    @Operation(summary = "Autenticação", description = "autenticas os usuarios", tags = "Usuario")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/validatorUser/{token}")
    @Operation(summary = "Validar Token", description = "Metodo utilizado para validar Token", tags = "Usuario")
    public boolean userValidator(@PathVariable String token) {
        return  jwtService.tokenValido(token);
    }

}
