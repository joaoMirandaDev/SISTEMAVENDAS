package com.example.Sistema.Colaborador.service;

import com.example.Sistema.Colaborador.DTO.ColaboradorCreateDto;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Colaborador.mapper.ColaboradorMapper;
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
import org.springframework.context.MessageSource;
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
    private final ColaboradorMapper mapper;
    private final DocumentosService documentosService;
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
            Colaborador colaborador = new Colaborador();
            colaborador.setAtivo(0);
            colaborador.setCargo(colaboradorDto.getCargo());
            colaborador.setCpf(colaboradorDto.getCpf());
            colaborador.setDataContratoInicial(colaboradorDto.getDataContratoInicial());
            colaborador.setDataNascimento(colaboradorDto.getDataNascimento());
            colaborador.setEmail(colaboradorDto.getEmail());
            colaborador.setNome(colaboradorDto.getNome());
            colaborador.setSobrenome(colaboradorDto.getSobrenome());
            colaborador.setRg(colaboradorDto.getRg());
            colaborador.setSexo(colaboradorDto.getSexo());
            colaborador.setSalario(colaboradorDto.getSalario());
            colaborador.setEndereco(colaboradorDto.getEndereco() == null ? null : enderecoService.add(colaboradorDto.getEndereco()));
            colaborador.setTelefone(colaboradorDto.getTelefone());
            if (Objects.nonNull(colaboradorDto.getFile()) && Objects.nonNull(colaboradorDto.getFile().getKey()) && !colaboradorDto.getFile().getKey().isEmpty() ) {
                colaborador.setDocumentos(documentosService.save(colaboradorDto.getFile()));
            }
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

    public Page<ColaboradorDto> findByPage(@Valid FilterColaborador filtro) {

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

//    public void editar(ColaboradorDto colaboradorDto) throws Exception {
//        try {
//            Colaborador colaborador = colaboradorRepository.findById(colaboradorDto.getId()).orElseThrow(() -> new NotFoundException(
//                    messageSource.getMessage("error.find", null, locale)
//            ));
//                colaborador.setNome(colaboradorDto.getNome());
//                colaborador.setSobrenome(colaboradorDto.getSobrenome());
//                colaborador.setCpf(colaboradorDto.getCpf());
//                colaborador.setRg(colaboradorDto.getRg());
//                colaborador.setNumero(colaboradorDto.getNumero());
//                colaborador.setCep(colaboradorDto.getCep());
//                colaborador.setRua(colaboradorDto.getRua());
//                colaborador.setBairro(colaboradorDto.getBairro());
//                colaborador.setCidade(colaboradorDto.getCidade());
//                colaborador.setEstado(colaboradorDto.getEstado());
//                colaborador.setTelefone(colaboradorDto.getTelefone());
//                colaboradorRepository.save(colaborador);
//
//            if (Objects.nonNull(colaboradorDto.getSenha()) && !colaboradorDto.getSenha().isEmpty()) {
//                Usuario usuario = usuarioRepository.findByLogin(colaboradorDto.getCpf());
//                String senhaCripto = passwordEncoder.encode(colaboradorDto.getSenha());
//                usuario.setSenha(senhaCripto);
//                usuarioRepository.save(usuario);
//            }
//        } catch (DataAccessException e) {
//            throw new Exception(messageSource.getMessage("error.save", null, locale),e);
//        }
//    }

    public ColaboradorDto getColaboradorByCpf(@NotNull @NotEmpty String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        return mapper.colaboradorToColaboradorDto(colaboradorRepository.findByCpf(cpf));
    }
}