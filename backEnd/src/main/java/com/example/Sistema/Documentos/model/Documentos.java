package com.example.Sistema.Documentos.model;


import com.example.Sistema.Usuario.model.Usuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "documentos")
@Getter
@Setter
@RequiredArgsConstructor
public class Documentos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "nome", columnDefinition = "TEXT")
    private String nome;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String route;

}