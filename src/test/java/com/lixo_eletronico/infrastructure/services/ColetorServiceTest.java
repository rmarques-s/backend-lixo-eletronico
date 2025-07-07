package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.infrastructure.usecases.UsuarioColetorData;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;
import com.lixo_eletronico.shared.dto.CreateUsuarioColetorRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioColetorResponseDTO;
import com.lixo_eletronico.shared.dto.DadosPerfilResponseDTO;
import com.lixo_eletronico.enums.RolesEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ColetorServiceTest {

    private ICreateUsuario handler;
    private ColetorService service;

    @BeforeEach
    void setUp() {
        handler = mock(ICreateUsuario.class);
        service = new ColetorService(handler);
    }

    @Test
    void cadastrarUsuario_deveRetornarResponse_quandoCriacaoForBemSucedida() {
        CreateUsuarioColetorRequestDTO request = new CreateUsuarioColetorRequestDTO();
        request.setNome("João");
        request.setSobrenome("Silva");
        request.setEmail("joao@example.com");
        request.setSenha("123456");
        request.setCpf("123.456.789-00");
        request.setCnpj("12.345.678/0001-99");

        // Criar um mock de retorno válido
        UsuarioColetorData retorno = new UsuarioColetorData();
        retorno.setCriado(true);
        retorno.setEmail("joao@example.com");
        retorno.setRole(RolesEnum.COLETOR.toString());

        when(handler.execute(any(UsuarioColetorData.class))).thenReturn(retorno);

        // Act
        CreateUsuarioColetorResponseDTO response = service.cadastrarUsuario(request);

        // Assert
        assertNotNull(response);
        assertEquals("João", response.getPrimeiroNome());
        assertEquals("Silva", response.getSobrenome());
        assertEquals("joao@example.com", response.getEmail());
        assertEquals("Usuário criado com sucesso", response.getMensagem());
        assertEquals(RolesEnum.COLETOR.toString(), response.getRole());
    }


    @Test
    void cadastrarUsuario_deveLancarExcecao_quandoCriacaoFalhar() {
        // Arrange
        CreateUsuarioColetorRequestDTO request = new CreateUsuarioColetorRequestDTO();
        request.setNome("João");
        request.setSobrenome("Silva");
        request.setEmail("joao@example.com");
        request.setSenha("123456");
        request.setCpf("123.456.789-00");
        request.setCnpj("12.345.678/0001-99");

        UsuarioColetorData responseDTO = new UsuarioColetorData();
        responseDTO.setCriado(false);
        responseDTO.setMotivoErroCriacao("Email já cadastrado");

        when(handler.execute(any(UsuarioColetorData.class))).thenReturn(responseDTO);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> service.cadastrarUsuario(request));

        assertEquals("400 BAD_REQUEST \"Erro ao criar cliente: Email já cadastrado\"", exception.getMessage());
    }

    @Test
    void cadastrarUsuario_devePropagarExcecao_doHandler() {
        // Arrange
        CreateUsuarioColetorRequestDTO request = new CreateUsuarioColetorRequestDTO();
        request.setNome("João");
        request.setSobrenome("Silva");
        request.setEmail("joao@example.com");
        request.setSenha("123456");
        request.setCpf("123.456.789-00");
        request.setCnpj("12.345.678/0001-99");

        when(handler.execute(any(UsuarioColetorData.class))).thenThrow(new RuntimeException("Erro inesperado"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.cadastrarUsuario(request));

        assertEquals("Erro inesperado", exception.getMessage());
    }
}
