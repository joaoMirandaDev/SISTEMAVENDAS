package com.example.Sistema.Usuario.services;

import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Role.model.Role;
import com.example.Sistema.Role.service.RoleService;
import com.example.Sistema.Usuario.DTO.UsuarioDTO;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.Interfaces.RolesInterface;
import com.example.Sistema.Usuario.model.Usuario;
import com.example.Sistema.Usuario.repository.UsuarioRepository;
import com.example.Sistema.Utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findById(Short id) {
        return usuarioRepository.findById(id);
    }

    public UserDetails autenticar( Usuario usuario ) throws Exception {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches( usuario.getSenha(), user.getPassword() );
        if(senhasBatem){
            return user;
        }
        throw new Exception("Senha invalida");
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByLogin(username);

        String[] roles = new String[]{};

        if(usuario.getRole().getName().equals(RolesInterface.ADMIN)) {
            roles  = new String[]{RolesInterface.ADMIN};
        } else if(usuario.getRole().getName().equals(RolesInterface.PROPRIETARIO)) {
            roles =  new String[]{RolesInterface.PROPRIETARIO};
        } else if (usuario.getRole().getName().equals(RolesInterface.CAIXA)) {
            roles = new String[]{RolesInterface.CAIXA};
        }

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }


    public UsuarioDTO findByLogin(String login) {
        Usuario user = usuarioRepository.findByLogin(login);
        return new UsuarioDTO(user.getId(),user.getRole());
    }

    public void createNewUser(Integer idRole, Colaborador colaborador) {

        Usuario usuario = new Usuario();
        usuario.setLogin(colaborador.getCpf());
        Role role = roleService.findById(idRole);
        usuario.setRole(role);
        usuario.setSenha("$2a$10$IAbOgyrcbEUgEHjrC4wWVuYgQcWFK7Hc/n2uIQ8HYGwVgfU6UETh6");
        usuario.setColaborador(colaborador);
        usuarioRepository.save(usuario);

    }
}
