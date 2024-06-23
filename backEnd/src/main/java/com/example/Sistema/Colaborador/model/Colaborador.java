package com.example.Sistema.Colaborador.model;

import com.example.Sistema.Documentos.model.Documentos;
import com.example.Sistema.Endereco.model.Endereco;
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
    @Column(columnDefinition = "TEXT")
    private String cargo;
    private Date dataContratoFinal;
    private String sexo;
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String cpf;
    @Column(unique = true, nullable = false)
    private String rg;
    @OneToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;
    private Integer isUsuario;
    private String telefone;
    private String email;
    private Integer ativo;
    @OneToOne
    @JoinColumn(name = "documento_id_photo", referencedColumnName = "id")
    private Documentos documentos;
    private Double salario;

    public Colaborador(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
