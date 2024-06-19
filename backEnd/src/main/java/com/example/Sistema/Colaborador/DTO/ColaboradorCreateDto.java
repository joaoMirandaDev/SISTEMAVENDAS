package com.example.Sistema.Colaborador.DTO;

import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Documentos.model.FileKey;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColaboradorCreateDto {

    private Integer id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String sobrenome;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataNascimento;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataContratoInicial;
    @NotEmpty
    private String sexo;
    @NotEmpty
    private String cpf;
    @NotEmpty
    private String rg;
    @NotEmpty
    private String cep;
    @NotEmpty
    private String rua;
    @NotEmpty
    private String bairro;
    @NotEmpty
    private String cidade;
    @NotEmpty
    private String estado;
    @NotEmpty
    private String telefone;
    @NotEmpty
    private String cargo;
    private Integer isUsuario;
    private String email;
    private String senha;
    @NotEmpty
    private String numero;
    private Integer role;
    private Double salario;
    private FileKey file;

    public ColaboradorCreateDto(@NotNull Colaborador colaborador) {
        this.dataNascimento = colaborador.getDataNascimento();
        this.dataContratoInicial = colaborador.getDataContratoInicial();
        this.id = colaborador.getId();
        this.sobrenome = colaborador.getSobrenome();
        this.nome = colaborador.getNome();
        this.cpf = colaborador.getCpf();
        this.numero = colaborador.getNumero();
        this.salario = colaborador.getSalario();
        this.rg = colaborador.getRg();
        this.cep = colaborador.getCep();
        this.rua = colaborador.getRua();
        this.bairro = colaborador.getBairro();
        this.cidade = colaborador.getCidade();
        this.estado = colaborador.getEstado();
        this.telefone = colaborador.getTelefone();
        this.email = colaborador.getEmail();
    }

    public ColaboradorCreateDto(String nome) {
        this.nome = nome;
    }
}
