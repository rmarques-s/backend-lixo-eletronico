package com.lixo_eletronico.infrastructure.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.enums.TipoUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.interfaces.controllers.EmpresaDisponivelDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientesPontoServicoServiceTest {

    @Mock
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @InjectMocks
    private ClientesPontoServicoService service;

    @Test
    void listarEmpresasDisponiveis_deveRetornarListaDeEmpresas() {
        PerfilUsuario empresa1 = new PerfilUsuario();
        empresa1.setId(1L);
        empresa1.setNome("Empresa 1");

        PerfilUsuario empresa2 = new PerfilUsuario();
        empresa2.setId(2L);
        empresa2.setNome("Empresa 2");

        when(perfilUsuarioRepository.findAllByTipoUsuario(TipoUsuario.EMPRESA))
            .thenReturn(List.of(empresa1, empresa2));

        List<EmpresaDisponivelDTO> resultado = service.listarEmpresasDisponiveis();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Empresa 1", resultado.get(0).getNome());
        assertEquals("Empresa 2", resultado.get(1).getNome());
    }

    @Test
    void listarEmpresasDisponiveis_deveRetornarListaVaziaSeNaoHouverEmpresas() {
        when(perfilUsuarioRepository.findAllByTipoUsuario(TipoUsuario.EMPRESA))
            .thenReturn(Collections.emptyList());

        List<EmpresaDisponivelDTO> resultado = service.listarEmpresasDisponiveis();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
