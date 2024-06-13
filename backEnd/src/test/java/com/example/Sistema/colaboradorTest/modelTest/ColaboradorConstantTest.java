package com.example.Sistema.colaboradorTest.modelTest;

import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;

import java.util.Date;

public class ColaboradorConstantTest {

   private static Colaborador colaborador = new Colaborador(null, "JOAO", "SOUZA", new Date(),null,null,
            "MASCULINO", "79722490672", "19517206", "35053060", "AV.DO CANAL", "ALTINOPOLIS", "GOVERNADOR VALADARES",
            "MG", 0 ,"33999471753", "joao@test.com", "505", null, null);

    public static final ColaboradorDto COLABORADOR = new ColaboradorDto(colaborador);

    public static final ColaboradorDto COLABORADOR_IS_EMPTY = new ColaboradorDto();

    public static final Colaborador INVALID_COLABORADOR = new Colaborador(null, "", "", new Date(),null,null,
            "", "", "", "", "", "", "",
            "", 0 ,"", "", "", null, null);

}
