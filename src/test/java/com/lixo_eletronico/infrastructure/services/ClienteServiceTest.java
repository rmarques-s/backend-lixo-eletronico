package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.enums.RolesEnum;
import com.lixo_eletronico.infrastructure.usecases.UsuarioClienteData;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteResponseDTO;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private ICreateUsuario handler;

    @Mock
    private PerfilUsuarioRepository perfilRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarUsuario_sucesso() {
        // Arrange
        CreateUsuarioClienteRequestDTO request = new CreateUsuarioClienteRequestDTO();
        request.setEmail("teste@email.com");
        request.setSenha("123456");
        request.setPrimeiroNome("Arthur");
        request.setSobrenome("Cruz");

        UsuarioClienteData resultado = new UsuarioClienteData();
        resultado.setCriado(true);
        resultado.setEmail("teste@email.com");
        resultado.setRole(RolesEnum.CLIENTE.toString());

        when(handler.execute(any(UsuarioClienteData.class))).thenReturn(resultado);

        // Act
        CreateUsuarioClienteResponseDTO response = clienteService.cadastrarUsuario(request);

        // Assert
        assertEquals("Arthur", response.getPrimeiroNome());
        assertEquals("Cruz", response.getSobrenome());
        assertEquals("teste@email.com", response.getEmail());
        assertEquals("Usuário criado com sucesso", response.getMensagem());
        assertEquals("CLIENTE", response.getRole());
    }

    @Test
    public void testCadastrarUsuario_falhaCriacao() {
        // Arrange
        CreateUsuarioClienteRequestDTO request = new CreateUsuarioClienteRequestDTO();
        request.setEmail("falha@email.com");
        request.setSenha("123");
        request.setPrimeiroNome("João");
        request.setSobrenome("Erro");

        UsuarioClienteData resultado = new UsuarioClienteData();
        resultado.setCriado(false);
        resultado.setMotivoErroCriacao("Email já cadastrado");

        when(handler.execute(any(UsuarioClienteData.class))).thenReturn(resultado);

        // Act + Assert
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () ->
                clienteService.cadastrarUsuario(request));

        assertTrue(ex.getReason().contains("Erro ao criar cliente: Email já cadastrado"));
    }
}
