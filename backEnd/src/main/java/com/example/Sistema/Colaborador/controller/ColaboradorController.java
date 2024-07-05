package com.example.Sistema.Colaborador.controller;

import com.example.Sistema.Colaborador.DTO.ColaboradorCreateDto;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.filtro.Filtro;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Colaborador.service.ColaboradorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;
    private final MessageSource messageSource;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @Operation(summary = "Cadastro de colaborador", description = "Metodo utilizado para cadastrar os colaboradores", tags = "Colaborador")
    public ResponseEntity<String> createNewColaborador(@Valid @RequestBody ColaboradorCreateDto colaboradorDto) throws Exception {
        colaboradorService.create(colaboradorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("success.created", null, LocaleInteface.BR));
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = "application/json")
    @Operation(summary = "FindById", description = "Metodo utilizado para resgatar o colaborador por ID", tags = "Colaborador")
    public ColaboradorCreateDto findById(@Positive @NotNull @PathVariable("id") Integer id) {
        return colaboradorService.findColaboradorById(id);
    }

    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @Operation(summary = "Deletar colaborador", description = "Metodo utilizado para deletar os colaboradores", tags = "Colaborador")
    public ResponseEntity<String> deleteById(@NotNull @Positive @PathVariable("id") Integer id) {
        try {
            colaboradorService.deleteById(id);
          return ResponseEntity.ok(messageSource.getMessage("success.delete", null, LocaleInteface.BR));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(messageSource.getMessage("error.delete", null, LocaleInteface.BR));
        }
    }


    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = "application/json")
    @Operation(summary = "Paginação de colaborador", description = "Metodo utilizado para buscar os colaboradores paginados", tags = "Colaborador")
    public ResponseEntity<Page<ColaboradorDto>> findAllByPage(@Valid @RequestBody FilterColaborador filtro) {
        return ResponseEntity.ok(colaboradorService.findByPage(filtro));
    }

    @PutMapping(value = "/editar", produces = "application/json")
    @Operation(summary = "Editar colaborador", description = "Metodo utilizado para editar os colaboradores por ID", tags = "Colaborador")
    public void editar(@RequestBody ColaboradorCreateDto colaboradorDto) throws Exception {
        colaboradorService.edit(colaboradorDto);
    }

    @RequestMapping(value = "/findByCpfCnpj/{cpf}", method = RequestMethod.GET,produces = "application/json")
    @Operation(summary = "getColaboradorByCpf", description = "Metodo utilizado para resgatar os colaboradores por CPF", tags = "Colaborador")
    public ColaboradorDto getColaboradorByCpf(@NotEmpty @PathVariable("cpf") String cpf) {
        return colaboradorService.getColaboradorByCpf(cpf);
    }
}

