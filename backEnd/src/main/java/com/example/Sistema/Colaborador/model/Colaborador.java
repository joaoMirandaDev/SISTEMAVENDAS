package com.example.Sistema.Colaborador.model;

import com.example.Sistema.Documentos.model.Documentos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "colaborador")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    @Column(columnDefinition = "TEXT", nullable = false)
    private String nome;
    private String sobrenome;
    private Date dataNascimento;
    private Date dataContratoInicial;
    private Date dataContratoFinal;
    private String sexo;
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String cpf;
    @Column(unique = true, nullable = false)
    private String rg;
    private String cep;
    private String rua;
    private String Bairro;
    private String cidade;
    private String estado;
    private Integer isUsuario;
    private String telefone;
    private String email;
    private String numero;
    private Integer ativo;
    @OneToOne
    @JoinColumn(name = "documento_id_photo", referencedColumnName = "id")
    private Documentos documentos;
    private Double salario;


}
