package com.example.Sistema.colaboradorTest.serviceTest;

import com.example.Sistema.Autenticacao.config.securityJwt.JwtService;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Colaborador.repository.ColaboradorRepository;
import com.example.Sistema.Colaborador.service.ColaboradorService;

import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.exceptions.NotFoundException;
import com.example.Sistema.Utils.pagination.Pagination;
import com.example.Sistema.colaboradorTest.modelTest.ColaboradorConstantTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.Sistema.colaboradorTest.modelTest.ColaboradorConstantTest.COLABORADOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTest {


    @InjectMocks
    private ColaboradorService colaboradorService;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private  MessageSource messageSource;

    @BeforeEach
    public void setUp() {
        colaboradorService = mock(ColaboradorService.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createColaborador_WithValidData_ReturnsString() throws Exception {
        Mockito.doNothing().when(colaboradorService).create(ColaboradorConstantTest.CREATE_COLABORADOR);
        colaboradorService.create(ColaboradorConstantTest.CREATE_COLABORADOR);

        verify(colaboradorService, times(1)).create(ColaboradorConstantTest.CREATE_COLABORADOR);
    }

    @Test
    public void createColaborador_WithValidData_ReturnsException() throws Exception {
        doThrow(new RuntimeException()).when(colaboradorService).create(ColaboradorConstantTest.COLABORADOR_IS_EMPTY);
        doThrow(new RuntimeException()).when(colaboradorService).create(ColaboradorConstantTest.INVALID_COLABORADOR);

        assertThatThrownBy(() -> colaboradorService.create(ColaboradorConstantTest.COLABORADOR_IS_EMPTY))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> colaboradorService.create(ColaboradorConstantTest.INVALID_COLABORADOR))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void colaborador_WithValidData_ReturnsPage() {
        FilterColaborador filtro = new FilterColaborador("nome" ,false, 10, 0, "JOAO");
        Pageable pageable = Pagination.createPageableFromFiltro(filtro, "nome");
        List<ColaboradorDto> listContent = Arrays.asList(
                COLABORADOR
        );
        Page<ColaboradorDto> pageTest = new PageImpl<>(listContent, pageable, 1);
        Mockito.when(colaboradorService.findAllByPage(filtro)).thenReturn(pageTest);

        Page<ColaboradorDto> sut = colaboradorService.findAllByPage(filtro);
        assertFalse(sut.getContent().isEmpty());
        assertNotNull("$.content");
        assertEquals(1, sut.getTotalElements());
        assertEquals("JOAO", sut.getContent().get(0).getNome());
    }

    @Test
    public void colaborador_WithValidData_ReturnsException() {
        FilterColaborador INVALID_FILTRO = new FilterColaborador("" ,false, 10, 0, "JOAO");
        FilterColaborador FILTRO_IS_EMPTY = new FilterColaborador();

        Mockito.when(colaboradorService.findAllByPage(INVALID_FILTRO)).thenThrow(RuntimeException.class);
        Mockito.when(colaboradorService.findAllByPage(FILTRO_IS_EMPTY)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> colaboradorService.findAllByPage(INVALID_FILTRO)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> colaboradorService.findAllByPage(FILTRO_IS_EMPTY)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getColaborador_ByExistingId_ReturnsColaborador() {
        Colaborador colaboradorMock = new Colaborador();
        when(colaboradorRepository.findById(1)).thenReturn(Optional.of(colaboradorMock));

        ColaboradorDto expectedDto = new ColaboradorDto(colaboradorMock);

        Colaborador colaborador = colaboradorRepository.findById(1).orElseThrow(() ->
                new NotFoundException(messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR))
        );
        ColaboradorDto sut = new ColaboradorDto(colaborador);


        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(expectedDto);
    }

    @Test
    public void getColaborador_ByExistingId_ReturnsThrown() {
        when(colaboradorService.findById(-1)).thenThrow(NotFoundException.class);
        assertThatThrownBy(() -> colaboradorService.findById(-1)).isInstanceOf(NotFoundException.class)
                .hasMessage(messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR));
    }
}
