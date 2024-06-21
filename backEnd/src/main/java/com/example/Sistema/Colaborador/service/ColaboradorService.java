package com.example.Sistema.Colaborador.service;

import com.example.Sistema.Colaborador.DTO.ColaboradorCreateDto;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Colaborador.specification.ColaboradorSpecification;
import com.example.Sistema.Documentos.service.DocumentosService;
import com.example.Sistema.Role.model.Role;
import com.example.Sistema.Usuario.services.UsuarioService;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.exceptions.NotFoundException;
import com.example.Sistema.Utils.filtro.Filtro;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Colaborador.repository.ColaboradorRepository;
import com.example.Sistema.Usuario.model.Usuario;
import com.example.Sistema.Role.repository.RoleRepository;
import com.example.Sistema.Usuario.repository.UsuarioRepository;
import com.example.Sistema.Utils.pagination.Pagination;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final MessageSource messageSource;
    private final DocumentosService documentosService;
    private final UsuarioService usuarioService;

    private final ColaboradorRepository colaboradorRepository;
    private static final Map<String, String> CAMPO_ORDENACAO = new HashMap<>();

    static {
        CAMPO_ORDENACAO.put("nome", "nome");
        CAMPO_ORDENACAO.put("cpf", "cpf");
        CAMPO_ORDENACAO.put("telefone", "telefone");
    }

    @Transactional(rollbackFor = Exception.class)
    public void create( ColaboradorCreateDto colaboradorDto) throws Exception {
        try {
            Colaborador colaborador = new Colaborador();
            colaborador.setAtivo(0);
            colaborador.setCargo(colaboradorDto.getCargo());
            colaborador.setCep(colaboradorDto.getCep());
            colaborador.setCidade(colaboradorDto.getCidade());
            colaborador.setCpf(colaboradorDto.getCpf());
            colaborador.setDataContratoInicial(colaboradorDto.getDataContratoInicial());
            colaborador.setDataNascimento(colaboradorDto.getDataNascimento());
            colaborador.setEmail(colaboradorDto.getEmail());
            colaborador.setEstado(colaboradorDto.getEstado());
            colaborador.setBairro(colaboradorDto.getBairro());
            colaborador.setNome(colaboradorDto.getNome());
            colaborador.setSobrenome(colaboradorDto.getSobrenome());
            colaborador.setRg(colaboradorDto.getRg());
            colaborador.setNumero(colaboradorDto.getNumero());
            colaborador.setRua(colaboradorDto.getRua());
            colaborador.setSexo(colaboradorDto.getSexo());
            colaborador.setSalario(colaboradorDto.getSalario());
            colaborador.setTelefone(colaboradorDto.getTelefone());
            if (Objects.nonNull(colaboradorDto.getFile()) && Objects.nonNull(colaboradorDto.getFile().getKey()) && !colaboradorDto.getFile().getKey().isEmpty() ) {
                colaborador.setDocumentos(documentosService.save(colaboradorDto.getFile()));
            }
            colaboradorRepository.save(colaborador);
            if (colaboradorDto.getIsUsuario() == 1) {
                usuarioService.createNewUser(colaboradorDto.getSenha(),colaboradorDto.getRole(), colaborador);
            }
        } catch (DataAccessException e) {
            throw new Exception(messageSource.getMessage("error.save", null, LocaleInteface.BR),e);
        }
    }

    public ColaboradorDto findById(Integer id) {
        Colaborador colaborador =  colaboradorRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR)
        ));
        return new ColaboradorDto(colaborador);
    }

    public Page<ColaboradorDto> findByPage(FilterColaborador filtro) {
        if (Objects.isNull(filtro.getId())) {
            throw new IllegalArgumentException(messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR));
        }
        Pageable pageable = Pagination.createPageableFromFiltro(filtro, "nome");
        Page<Colaborador> colaboradorPage = colaboradorRepository.findAll(ColaboradorSpecification.FiltroColaborador(filtro), pageable);
        if (Objects.nonNull(colaboradorPage) && !colaboradorPage.getContent().isEmpty()) {
            return colaboradorPage.map(ColaboradorDto::new);
        }
        return Page.empty();
    }


    public void remove(Integer id) {
        colaboradorRepository.deleteById(id);
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

    public ColaboradorDto getColaboradorByCpf(@NotEmpty String cpf) {
        if (cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        Colaborador colaborador = colaboradorRepository.findByCpf(cpf);
        return new ColaboradorDto(colaborador.getNome());
    }
}
