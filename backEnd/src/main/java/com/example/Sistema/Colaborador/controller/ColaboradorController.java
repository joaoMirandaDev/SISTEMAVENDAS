package com.example.Sistema.Colaborador.controller;

import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.filtro.Filtro;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Colaborador.service.ColaboradorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/colaborador")
public class ColaboradorController {

    private final ColaboradorService colaboradorService;
    private final MessageSource messageSource;

    @PostMapping(value = "/create", produces = "application/json")
    @Operation(summary = "Cadastro de colaborador", description = "Metodo utilizado para cadastrar os colaboradores", tags = "Colaborador")
    public ResponseEntity<String> createNewColaborador(@Valid @RequestBody ColaboradorDto colaboradorDto) throws Exception {
        colaboradorService.create(colaboradorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageSource.getMessage("success.created", null, LocaleInteface.BR));
    }

    @GetMapping(value = "/findById/{id}", produces = "application/json")
    @Operation(summary = "FindById", description = "Metodo utilizado para resgatar o colaborador por ID", tags = "Colaborador")
    public ColaboradorDto findById(@PathVariable Integer id) {
        return colaboradorService.findById(id);
    }

    @DeleteMapping(value = "/deleteById/{id}", produces = "application/json")
    @Operation(summary = "Deletar colaborador", description = "Metodo utilizado para deletar os colaboradores", tags = "Colaborador")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        return colaboradorService.deleteById(id);
    }


    @PostMapping(value = "/list", produces = "application/json")
    @Operation(summary = "Paginação de colaborador", description = "Metodo utilizado para buscar os colaboradores paginados", tags = "Colaborador")
    public ResponseEntity<Page<ColaboradorDto>> findAllByPage(@Valid @RequestBody FilterColaborador filtro) {
        return ResponseEntity.ok(colaboradorService.findAllByPage(filtro));
    }

//    @PutMapping(value = "/editar", produces = "application/json")
//    @Operation(summary = "Editar colaborador", description = "Metodo utilizado para editar os colaboradores por ID", tags = "Colaborador")
//    public void editar(@RequestBody ColaboradorDto colaboradorDto) throws Exception {
//        colaboradorService.editar(colaboradorDto);
//    }

    @GetMapping(value = "/findByCpfCnpj/{CpfCnpj}", produces = "application/json")
    @Operation(summary = "findByCpfCnpj", description = "Metodo utilizado para resgatar os colaboradores por CPF", tags = "Colaborador")
    public ColaboradorDto findById(@PathVariable String CpfCnpj) {
        return colaboradorService.findByCpfCnpj(CpfCnpj);
    }
}
