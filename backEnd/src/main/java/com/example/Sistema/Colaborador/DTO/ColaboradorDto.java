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

import javax.validation.constraints.NotEmpty;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColaboradorDto {

    private Integer id;
    @NotEmpty(message = "Nome não pode estar vazio")
    private String nome;
    private String sobrenome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataNascimento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataContratoInicial;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date dataContratoFinal;
    private String sexo;
    @NotEmpty(message = "CPF não pode estar vazio")
    private String cpf;
    private String rg;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone;
    private Integer isUsuario;
    private String email;
    private String ativo;
    private String numero;
    private Integer roleId;
    private Double salario;
    private FileName file;

    public ColaboradorDto(Colaborador colaborador) {
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
