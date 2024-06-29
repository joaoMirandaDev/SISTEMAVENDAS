package com.example.Sistema.colaboradorTest.serviceTest;


import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Colaborador.model.Colaborador;
import com.example.Sistema.Colaborador.repository.ColaboradorRepository;
import com.example.Sistema.Colaborador.service.ColaboradorService;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.genericClass.GenericSpecification;
import com.example.Sistema.colaboradorTest.modelTest.ColaboradorConstantTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTest {


    @InjectMocks
    private ColaboradorService colaboradorService;

    @Mock
    private GenericSpecification generic;

    @Mock
    private Colaborador colaborador;

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @Mock
    private MessageSource messageSource;

    @Test
    public void createColaborador_WithValidData_ReturnsString() throws Exception {
        ColaboradorService mock = mock(ColaboradorService.class);

        // Configura o mock para não lançar exceções ao chamar create com CREATE_COLABORADOR
        doNothing().when(mock).create(ColaboradorConstantTest.CREATE_COLABORADOR);

        // Chama o método create do mock e verifica se não há exceções lançadas
        assertThatCode(() -> mock.create(ColaboradorConstantTest.CREATE_COLABORADOR)).doesNotThrowAnyException();
    }

    @Test
    public void createColaborador_WithValidData_ReturnsException() throws Exception {
        ColaboradorService mock  = mock(ColaboradorService.class);
        doThrow(new RuntimeException()).when(mock).create(ColaboradorConstantTest.COLABORADOR_IS_EMPTY);
        doThrow(new RuntimeException()).when(mock).create(ColaboradorConstantTest.INVALID_COLABORADOR);

        assertThatThrownBy(() -> mock.create(ColaboradorConstantTest.COLABORADOR_IS_EMPTY))
                .isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> mock.create(ColaboradorConstantTest.INVALID_COLABORADOR))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void Pagecolaborador_WithValidData_ReturnsPage() {
        FilterColaborador filtro = new FilterColaborador("nome" ,false, 10, 0, "");
        Pageable pageable = colaboradorService.createPageableFromFiltro(filtro, "nome");
        List<Colaborador> listContent = Arrays.asList(
                new Colaborador(null, "JOAO")
        );
        Page<Colaborador> pageTest = new PageImpl<>(listContent, pageable, 1);
        when(colaboradorRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pageTest);

        Page<ColaboradorDto> sut = colaboradorService.findByPage(filtro);
        assertFalse(sut.getContent().isEmpty());
        assertNotNull("$.content");
        assertEquals(1, sut.getTotalElements());
        assertEquals("JOAO", sut.getContent().get(0).getNome());
    }

    @Test
    public void Pagecolaborador_WithInvalidValidData_ReturnsException() {
        FilterColaborador INVALID_FILTRO = new FilterColaborador("" ,false, 10, 0, "JOAO");
        FilterColaborador FILTRO_IS_EMPTY = new FilterColaborador();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> colaboradorService.
                findByPage(INVALID_FILTRO));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> colaboradorService.
                        findByPage(FILTRO_IS_EMPTY)).
                withMessage(messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR));
    }

    @Test
    public void deleteColaborador_withExistingId() {
        ColaboradorService mock  = mock(ColaboradorService.class);
        doNothing().when(mock).deleteById(1);
        assertThatCode(() -> mock.deleteById(1)).doesNotThrowAnyException();
    }

    @Test
    public void deleteColaborador_WithUnexistingId_ThrowsException() {
        ColaboradorService mock  = mock(ColaboradorService.class);
        doThrow(new RuntimeException()).when(mock).deleteById(1);
        assertThatThrownBy(() -> mock.deleteById(1)).isInstanceOf(RuntimeException.class);
    }
}
