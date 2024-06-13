package com.example.Sistema.colaboradorTest.controllerTest;

import com.example.Sistema.Autenticacao.config.securityJwt.JwtService;
import com.example.Sistema.Colaborador.DTO.ColaboradorDto;
import com.example.Sistema.Colaborador.controller.ColaboradorController;
import com.example.Sistema.Colaborador.service.ColaboradorService;

import com.example.Sistema.Usuario.services.UsuarioService;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.colaboradorTest.modelTest.ColaboradorConstantTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest(ColaboradorController.class)
public class ColaboradorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageSource messageSource;

    @MockBean
    private ColaboradorService colaboradorService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void createColaborador_WithValidData_ReturnsCreated() throws Exception {

        Mockito.doNothing().when(colaboradorService).create(ColaboradorConstantTest.COLABORADOR);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/colaborador/create")
                .content(objectMapper.writeValueAsString(ColaboradorConstantTest.COLABORADOR))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                .string(messageSource.getMessage("success.created", null , LocaleInteface.BR)));

    }

    @Test
    @WithMockUser(roles = {"CAIXA"})
    public void createColaborador_WithValidData_ReturnsForbidden() throws Exception {

        Mockito.doNothing().when(colaboradorService).create(ColaboradorConstantTest.COLABORADOR);
        String value = objectMapper.writeValueAsString(ColaboradorConstantTest.COLABORADOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/colaborador/create")
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void createColaborador_WithValidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/colaborador/create")
                        .content(objectMapper.writeValueAsString(ColaboradorConstantTest.INVALID_COLABORADOR))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/colaborador/create")
                        .content(objectMapper.writeValueAsString(ColaboradorConstantTest.COLABORADOR_IS_EMPTY))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}
