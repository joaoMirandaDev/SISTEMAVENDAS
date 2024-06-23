package com.example.Sistema.Colaborador.DTO;

import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Documentos.model.FileKey;
import com.example.Sistema.Endereco.Dto.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Objects;



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
    private String telefone;
    @NotEmpty
    private String cargo;
    private Integer isUsuario;
    private String email;
    private String ativo;
    private Integer roleId;
    private Double salario;
    private FileKey file;
    private EnderecoDTO endereco;


    public ColaboradorDto(@NotNull Colaborador colaborador) {

        this.dataNascimento = colaborador.getDataNascimento();
        this.dataContratoInicial = colaborador.getDataContratoInicial();
        this.dataContratoFinal = colaborador.getDataContratoFinal();
        this.id = colaborador.getId();
        this.sobrenome = colaborador.getSobrenome();
        this.nome = colaborador.getNome();
        this.cpf = colaborador.getCpf();
        this.salario = colaborador.getSalario();
        this.rg = colaborador.getRg();
        if (colaborador.getEndereco() != null) {
            this.endereco = new EnderecoDTO(colaborador.getEndereco());
        }
        this.telefone = colaborador.getTelefone();
        this.email = colaborador.getEmail();
        this.ativo = Objects.nonNull(colaborador.getAtivo()) && colaborador.getAtivo().equals(0) ? "Ativo" : "Inativo";
        if (Objects.nonNull(colaborador.getDocumentos())) {
            FileKey fileKey = new FileKey(colaborador.getDocumentos().getNome(), colaborador.getDocumentos().getRoute());
            this.file = fileKey;
        }
    }
}
