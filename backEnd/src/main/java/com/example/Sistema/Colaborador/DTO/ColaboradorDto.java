package com.example.Sistema.Colaborador.DTO;

import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Documentos.model.Documentos;
import com.example.Sistema.Documentos.model.FileName;
import com.example.Sistema.Usuario.DTO.UsuarioDTO;
import com.example.Sistema.Usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColaboradorDto {

    private Integer id;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String sobrenome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataNascimento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataContratoInicial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataContratoFinal;
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
    @PositiveOrZero
    private Integer isUsuario;
    private String email;
    @NotEmpty
    private String ativo;
    @NotEmpty
    private String numero;
    private Integer roleId;
    @PositiveOrZero
    private Double salario;
    private FileName file;

    public ColaboradorDto(@NotNull Colaborador colaborador) {
        this.dataNascimento = colaborador.getDataNascimento();
        this.dataContratoInicial = colaborador.getDataContratoInicial();
        this.dataContratoFinal = colaborador.getDataContratoFinal();
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
        this.ativo = colaborador.getAtivo() == 0 ? "Ativo" : "Inativo";
    }
    
    public ColaboradorDto(String nome) {
        this.nome = nome;
    }
}
