package com.example.Sistema.Colaborador.service;

import com.example.Sistema.Colaborador.DTO.ColaboradorCreateDto;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Documentos.service.DocumentosService;
import com.example.Sistema.Endereco.service.EnderecoService;
import com.example.Sistema.Usuario.services.UsuarioService;
import com.example.Sistema.Utils.genericClass.GenericSpecificationAndPegeable;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.exceptions.NotFoundException;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Colaborador.repository.ColaboradorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.*;

import static com.example.Sistema.Utils.genericClass.GenericSpecificationAndPegeable.*;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final MessageSource messageSource;
    private final DocumentosService documentosService;
    private final ModelMapper modelMapper;
    private final EnderecoService enderecoService;
    private final UsuarioService usuarioService;
    private final ColaboradorRepository colaboradorRepository;

    public Pageable createPageableFromFiltro(@Valid FilterColaborador filtro, String OrderInitial) {
        if (Objects.isNull(filtro.getId())) {
            filtro.setId(OrderInitial);
            filtro.setDesc(true);
        }

        Sort sort = filtro.isDesc() ? Sort.by(filtro.getId()).descending() : Sort.by(filtro.getId()).ascending();
        return PageRequest.of(filtro.getPagina(), filtro.getTamanhoPagina(), sort);
    }


    @Transactional(rollbackFor = Exception.class)
    public void create(@Valid ColaboradorCreateDto colaboradorDto) throws Exception {
        try {
            ModelMapper modelMapper = new ModelMapper();
            Colaborador colaborador =  modelMapper.map(colaboradorDto, Colaborador.class);
            if (Objects.nonNull(colaboradorDto.getFile()) && Objects.nonNull(colaboradorDto.getFile().getKey()) && !colaboradorDto.getFile().getKey().isEmpty() ) {
                colaborador.setDocumentos(documentosService.save(colaboradorDto.getFile()));
            }
            colaborador.setEndereco(enderecoService.add(colaboradorDto.getEndereco()));
            colaboradorRepository.save(colaborador);
            if (colaboradorDto.getIsUsuario() == 1 && !colaboradorDto.getSenha().isEmpty() && Objects.nonNull(colaboradorDto.getRole())) {
                usuarioService.createNewUser(colaboradorDto.getSenha(),colaboradorDto.getRole(), colaborador);
            }
        } catch (DataAccessException e) {
            throw new Exception(messageSource.getMessage("error.save", null, LocaleInteface.BR),e);
        }
    }

    public ColaboradorDto findById(@Positive @NotNull Integer id) {
        Colaborador colaborador =  colaboradorRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR)
        ));
        return new ColaboradorDto(colaborador);
    }

    public Page<ColaboradorDto> findByPage(FilterColaborador filtro) {
        if (filtro.getId() == null || filtro.getId().isEmpty()) {
            throw new IllegalArgumentException(messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR));
        }
        Pageable pageable = createPageableFromFiltro(filtro, "nome");

        Specification<Colaborador> specification = GenericSpecificationAndPegeable.
                <Colaborador>filterByProperty("nome",filtro.getNome())
                .and(filterByProperty("sobrenome",filtro.getSobrenome()))
                .and(filterByProperty("cpf",filtro.getCpf()))
                .and(filterByIdWithJoin("endereco","estado",filtro.getEstado()))
                .and(filterByIdWithJoin("endereco","cidade" ,filtro.getCidade()))
                .and(filterByPropertyInterger("ativo", filtro.getAtivo()));

        Page<Colaborador> colaboradorPage = colaboradorRepository.findAll(specification, pageable);
        if (Objects.nonNull(colaboradorPage) && !colaboradorPage.getContent().isEmpty()) {
            return colaboradorPage.map(ColaboradorDto::new);
        }
        return Page.empty();
    }


    public void deleteById(@NotNull @Positive Integer id) {
        if (colaboradorRepository.existsById(id)) {
            colaboradorRepository.deleteById(id);
        } else {
            throw new RuntimeException(messageSource.getMessage("erro.invalid.result.baseData", null, LocaleInteface.BR));
        }
    }

    public void edit(ColaboradorCreateDto dto) throws Exception {
        try {
            Colaborador colaborador = colaboradorRepository.save(modelMapper.map(dto,Colaborador.class));
            if (Objects.nonNull(dto.getFile()) && Objects.nonNull(dto.getFile().getKey()) && !dto.getFile().getKey().isEmpty() ) {
                colaborador.setDocumentos(documentosService.save(dto.getFile()));
            }
            if (dto.getIsUsuario() == 1 && !dto.getSenha().isEmpty() && Objects.nonNull(dto.getRole())) {
                usuarioService.editUser(dto.getSenha(),dto.getRole(), colaborador);
            }
        } catch (DataAccessException e) {
            throw new Exception(messageSource.getMessage("error.save", null, LocaleInteface.BR),e);
        }
    }

    public ColaboradorDto getColaboradorByCpf(@NotNull @NotEmpty String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        return modelMapper.map(colaboradorRepository.findByCpf(cpf),ColaboradorDto.class);
    }
}