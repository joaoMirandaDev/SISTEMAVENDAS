package com.example.Sistema.Colaborador.mapper;

import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColaboradorMapper {

    ColaboradorDto colaboradorToColaboradorDto(Colaborador colaborador);

}
