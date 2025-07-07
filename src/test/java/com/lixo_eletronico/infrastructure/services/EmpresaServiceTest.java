package com.lixo_eletronico.infrastructure.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.lixo_eletronico.enums.RolesEnum;
import com.lixo_eletronico.infrastructure.usecases.UsuarioEmpresaData;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;
import com.lixo_eletronico.shared.dto.CreateUsuarioEmpresaRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioEmpresaResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.server.ResponseStatusException;

class EmpresaServiceTest {

    private ICreateUsuario handler;
    private EmpresaService service;

    @BeforeEach
    void setUp() {
        handler = mock(ICreateUsuario.class);
        service = new EmpresaService(handler);
    }

    @Test
    void cadastrarUsuario_deveRetornarResponse_quandoCriacaoForBemSucedida() {
        // Arrange
        CreateUsuarioEmpresaRequestDTO request = new CreateUsuarioEmpresaRequestDTO();
        request.setNome("Empresa ABC");
        request.setEmail("empresa@abc.com");
        request.setSenha("senha123");
        request.setCnpj("00.000.000/0001-00");

        UsuarioEmpresaData retorno = new UsuarioEmpresaData();
        retorno.setCriado(true);
        retorno.setEmail("empresa@abc.com");
        retorno.setRole(RolesEnum.EMPRESA.toString());

        when(handler.execute(any(UsuarioEmpresaData.class))).thenReturn(retorno);

        // Act
        CreateUsuarioEmpresaResponseDTO response = service.cadastrarUsuario(request);

        // Assert
        assertNotNull(response);
        assertEquals("Empresa ABC", response.getNomeEmpresa());
        assertEquals("empresa@abc.com", response.getEmail());
        assertEquals("Usuário criado com sucesso", response.getMensagem());
        assertEquals(RolesEnum.EMPRESA.toString(), response.getRole());
    }

    @Test
    void cadastrarUsuario_deveLancarException_quandoHandlerNaoConseguirCriarUsuario() {
        // Arrange
        CreateUsuarioEmpresaRequestDTO request = new CreateUsuarioEmpresaRequestDTO();
        request.setNome("Empresa ABC");
        request.setEmail("empresa@abc.com");
        request.setSenha("senha123");
        request.setCnpj("00.000.000/0001-00");

        UsuarioEmpresaData retorno = new UsuarioEmpresaData();
        retorno.setCriado(false);
        retorno.setMotivoErroCriacao("Email já existe");

        when(handler.execute(any(UsuarioEmpresaData.class))).thenReturn(retorno);

        // Act + Assert
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.cadastrarUsuario(request));

        assertEquals("400 BAD_REQUEST \"Erro ao criar cliente: Email já existe\"", ex.getMessage());
    }

    @Test
    void cadastrarUsuario_devePropagarExcecao_quandoHandlerLancarErro() {
        // Arrange
        CreateUsuarioEmpresaRequestDTO request = new CreateUsuarioEmpresaRequestDTO();
        request.setNome("Empresa XYZ");
        request.setEmail("erro@xyz.com");
        request.setSenha("senha123");
        request.setCnpj("11.111.111/0001-11");

        when(handler.execute(any(UsuarioEmpresaData.class)))
            .thenThrow(new RuntimeException("Erro inesperado"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.cadastrarUsuario(request));

        assertEquals("Erro inesperado", ex.getMessage());
    }
}
