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

import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        Usuario usuario = usuarioRepository.findByLogin(username).get();

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
        Optional<Usuario> user = usuarioRepository.findByLogin(login);
        if (user.isPresent()) {
            return new UsuarioDTO(user.get().getId(),user.get().getRole());
        }
        return null;
    }

    public void createNewUser(String senha,Integer idRole, Colaborador colaborador) {
        Usuario usuario = new Usuario();
        usuario.setLogin(colaborador.getCpf());
        Role role = roleService.findById(idRole);
        usuario.setRole(role);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setColaborador(colaborador);
        usuarioRepository.save(usuario);

    }

    public void editUser(String senha, Integer idRole, Colaborador colaborador) {
        Usuario usuario = usuarioRepository.findByLogin(colaborador.getCpf()).get();
        if (Objects.isNull(usuario)) {
            this.createNewUser(senha,idRole,colaborador);
        } else {
            usuario.setLogin(colaborador.getCpf());
            Role role = roleService.findById(idRole);
            usuario.setRole(role);
            usuario.setSenha(passwordEncoder.encode(senha));
            usuario.setColaborador(colaborador);
            usuarioRepository.save(usuario);
        }
    }

    public void deleteByLogin(String cpf) {
        Optional<Usuario> user = usuarioRepository.findByLogin(cpf);
        if (user.isPresent()) {
            usuarioRepository.delete(user.get());
        }
    }
}
