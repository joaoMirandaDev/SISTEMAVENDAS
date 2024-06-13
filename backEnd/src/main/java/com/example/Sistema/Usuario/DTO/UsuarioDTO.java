package com.example.Sistema.Usuario.DTO;

import com.example.Sistema.Role.DTO.RoleDTO;
import com.example.Sistema.Role.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UsuarioDTO {
    private Integer id;
    private String login;
    private String senha;
    private RoleDTO role;

    public UsuarioDTO(Integer id, String login ,Role role) {
        this.id = id;
        this.login = login;
        this.role = new RoleDTO(role.getId(), role.getName());
    }

    public UsuarioDTO(Integer id, Role role) {
        this.id = id;
        this.role = new RoleDTO(role.getId(), role.getName());
    }
}
