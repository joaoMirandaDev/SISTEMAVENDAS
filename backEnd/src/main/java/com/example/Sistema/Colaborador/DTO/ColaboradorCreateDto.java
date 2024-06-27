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
    private String senha;
    private Integer role;
    private Double salario;
    private FileKey file;
    private EnderecoDTO endereco;
    private Integer ativo;
}
