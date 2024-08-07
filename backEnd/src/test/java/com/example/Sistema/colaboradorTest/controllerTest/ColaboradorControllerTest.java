package com.example.Sistema.colaboradorTest.controllerTest;

import com.example.Sistema.Autenticacao.config.securityJwt.JwtService;

import com.example.Sistema.Colaborador.controller.ColaboradorController;
import com.example.Sistema.Colaborador.filter.FilterColaborador;
import com.example.Sistema.Colaborador.repository.ColaboradorRepository;
import com.example.Sistema.Colaborador.service.ColaboradorService;


import com.example.Sistema.Usuario.services.UsuarioService;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;

import com.example.Sistema.colaboradorTest.modelTest.ColaboradorConstantTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private ColaboradorRepository colaboradorRepository;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    private static final Map<String, String> CAMPO_ORDENACAO = new HashMap<>();
    static {
        CAMPO_ORDENACAO.put("nome", "nome");
        CAMPO_ORDENACAO.put("cpf", "cpf");
        CAMPO_ORDENACAO.put("telefone", "telefone");
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void createColaborador_WithValidData_ReturnsCreated() throws Exception {

        Mockito.doNothing().when(colaboradorService).create(ColaboradorConstantTest.CREATE_COLABORADOR);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/colaborador/create")
                .content(objectMapper.writeValueAsString(ColaboradorConstantTest.CREATE_COLABORADOR))
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                .string(messageSource.getMessage("success.created", null , LocaleInteface.BR)));

    }

    @Test
    @WithMockUser(roles = {"CAIXA"})
    public void createColaborador_WithValidData_ReturnsForbidden() throws Exception {

        Mockito.doNothing().when(colaboradorService).create(ColaboradorConstantTest.CREATE_COLABORADOR);
        String value = objectMapper.writeValueAsString(ColaboradorConstantTest.COLABORADOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/colaborador/create")
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void createColaborador_WithValidData_ReturnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/colaborador/create")
                        .content(objectMapper.writeValueAsString(ColaboradorConstantTest.INVALID_COLABORADOR))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/colaborador/create")
                        .content(objectMapper.writeValueAsString(ColaboradorConstantTest.COLABORADOR_IS_EMPTY))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void Colaborador_WithValidData_ReturnsPage() throws Exception {

        FilterColaborador filtro = new FilterColaborador("nome", false, 10, 0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/colaborador/page")
                .content(objectMapper.writeValueAsString(filtro))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void Colaborador_WithInvalidValidData_ReturnsBadRequest() throws Exception {

        FilterColaborador filtro = new FilterColaborador(null, false, 10, 0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/colaborador/page")
                        .content(objectMapper.writeValueAsString(filtro))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void Colaborador_WithDeleteById_ReturnsString() throws Exception {
        Mockito.doNothing().when(colaboradorService).deleteById(1);
        mockMvc.perform(delete("/api/colaborador/deleteById/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Deletado com sucesso."));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void Colaborador_WithDeleteById_ReturnsThrown() throws Exception {

        doThrow(new RuntimeException()).when(colaboradorService).deleteById(1);
        mockMvc.perform(delete("/api/colaborador/deleteById/1"))
                .andExpect(status().isInternalServerError());

        doThrow(new NullPointerException()).when(colaboradorService).deleteById(null);
        mockMvc.perform(delete("/api/colaborador/deleteById/null"))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void GetColaborador_WithCpf_ReturnsColaboradorDTO() throws Exception {
        when(colaboradorService.getColaboradorByCpf("13226726609")).thenReturn(ColaboradorConstantTest.COLABORADOR);
        mockMvc.perform(get("/api/colaborador/findByCpfCnpj/13226726609"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ColaboradorConstantTest.COLABORADOR));
    }

}