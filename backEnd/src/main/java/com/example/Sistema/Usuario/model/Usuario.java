package com.example.Sistema.Usuario.model;

import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Role.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;
    @Column(nullable = false)
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;
    @OneToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;
    @OneToOne
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    private Colaborador colaborador;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
}
