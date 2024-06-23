package com.example.Sistema.colaboradorTest.modelTest;

import com.example.Sistema.Colaborador.DTO.ColaboradorCreateDto;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;

import java.util.Date;

public class ColaboradorConstantTest {

    public static final ColaboradorCreateDto CREATE_COLABORADOR = new ColaboradorCreateDto(null, "JOAO", "SOUZA"
            ,null, null, "MASCULINO", "13226726609", "19517206",
             "339994717553", "ALMOXARIFE",0, "JOAO@TESTE.COM","123456", 1, 1750.00, null, null);

    public static final ColaboradorDto COLABORADOR = new ColaboradorDto(1, "joao", "SOUZA"
            ,null, null,null, "MASCULINO", "13226726609", "19517206",
             "339994717553", "ALMOXARIFE",
            0, "JOAO@TESTE.COM", "ATIVO", 1, 1750.00, null, null);

    public static final ColaboradorCreateDto COLABORADOR_IS_EMPTY = new ColaboradorCreateDto();

    public static final ColaboradorCreateDto INVALID_COLABORADOR = new ColaboradorCreateDto(null, "", "SOUZA"
            ,null, null, "MASCULINO", "13226726609", "19517206"
            , "339994717553", "ALMOXARIFE",
            0, "JOAO@TESTE.COM","123456", 1, 1750.00, null, null);

}
