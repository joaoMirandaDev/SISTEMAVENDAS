package com.example.Sistema.Colaborador.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void create(ColaboradorDto colaboradorDto) throws Exception {
        try {
            Colaborador colaborador = new Colaborador();
            colaborador.setNome(colaboradorDto.getNome());
            colaborador.setSobrenome(colaboradorDto.getSobrenome());
            colaborador.setCpf(colaboradorDto.getCpf());
            colaborador.setRg(colaboradorDto.getRg());
            colaborador.setNumero(colaboradorDto.getNumero());
            colaborador.setCep(colaboradorDto.getCep());
            colaborador.setRua(colaboradorDto.getRua());
            colaborador.setSexo(colaboradorDto.getSexo());
            colaborador.setBairro(colaboradorDto.getBairro());
            colaborador.setCidade(colaboradorDto.getCidade());
            colaborador.setSalario(colaboradorDto.getSalario());
            colaborador.setEstado(colaboradorDto.getEstado());
            colaborador.setDataNascimento(colaboradorDto.getDataNascimento());
            colaborador.setDataContratoInicial(colaboradorDto.getDataContratoInicial());
            colaborador.setDataContratoFinal(colaboradorDto.getDataContratoFinal());
            colaborador.setTelefone(colaboradorDto.getTelefone());
            colaborador.setDocumentos(documentosService.save(colaboradorDto.getFile()));
            colaboradorRepository.save(colaborador);
            if (Optional.ofNullable(colaboradorDto.getIsUsuario()).orElse(0) == 1) {
                usuarioService.createNewUser(colaboradorDto.getRoleId(), colaborador);
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


    public ResponseEntity<String> deleteById(Integer id) {
        colaboradorRepository.deleteById(id);
        return ResponseEntity.ok(messageSource.getMessage("success.delete", null, LocaleInteface.BR));
    }

    public Page<ColaboradorDto> findAllByPage(FilterColaborador filtro) {
        Pageable pageable = Pagination.createPageableFromFiltro(filtro, CAMPO_ORDENACAO, "nome");
        return colaboradorRepository.findAll(ColaboradorSpecification.FiltroColaborador(filtro), pageable).map(ColaboradorDto::new);
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

    public ColaboradorDto findByCpfCnpj(String cpf) {
        Colaborador colaborador = colaboradorRepository.findByCpf(cpf);
        return new ColaboradorDto(colaborador.getNome());
    }
}
