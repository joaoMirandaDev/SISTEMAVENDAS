package com.example.Sistema.Endereco.mapper;


import com.example.Sistema.Endereco.Dto.EnderecoDTO;
import com.example.Sistema.Endereco.model.Endereco;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoDtoMapper {


    EnderecoDTO enderecoToEnderecoDto(Endereco endereco);

}
