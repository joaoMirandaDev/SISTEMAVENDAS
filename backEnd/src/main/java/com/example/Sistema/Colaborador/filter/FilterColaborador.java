package com.example.Sistema.Colaborador.filter;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FilterColaborador {
    @NotEmpty
    private String id;
    private boolean desc;
    private Integer tamanhoPagina = 10;
    private Integer pagina = 0;

    private String nome;
    private String sobrenome;
    private String cpf;
    private String estado;
    private String cidade;
    private Integer ativo;


    public FilterColaborador(String id, boolean desc, int tamanhoPagina, int pagina) {
        this.id = id;
        this.desc = desc;
        this.tamanhoPagina = tamanhoPagina;
        this.pagina = pagina;
    }

    public FilterColaborador(String id, boolean desc, int tamanhoPagina, int pagina, String nome) {
        this.id = id;
        this.desc = desc;
        this.tamanhoPagina = tamanhoPagina;
        this.pagina = pagina;
        this.nome = nome;
    }
}
