package com.example.Sistema.colaboradorTest.modelTest;

import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;

import java.util.Date;

public class ColaboradorConstantTest {


    public static final ColaboradorDto COLABORADOR = new ColaboradorDto(null, "JOAO", "SOUZA"
            ,new Date(), new Date(), null, "MASCULINO", "13226726609", "19517206",
            "35053060", "AVENIDA DO CANAL", "ALTINOPOLIS", "GV", "MG", "339994717553",
            0, "JOAO@TESTE.COM", "ATIVO", "341", null, 1750.00, null);

    public static final ColaboradorDto COLABORADOR_IS_EMPTY = new ColaboradorDto();

    public static final ColaboradorDto INVALID_COLABORADOR = new ColaboradorDto(null, "", "SOUZA"
            ,new Date(), new Date(), null, "MASCULINO", "13226726609", "19517206",
            "35053060", "AVENIDA DO CANAL", "ALTINOPOLIS", "GV", "MG", "339994717553",
            0, "JOAO@TESTE.COM", "ATIVO", "341", null, 1750.00, null);

}
