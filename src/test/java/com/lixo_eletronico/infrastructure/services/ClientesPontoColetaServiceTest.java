package com.lixo_eletronico.infrastructure.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.enums.TipoUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.shared.dto.ColetorDisponivelDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientesPontoColetaServiceTest {

    @Mock
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @InjectMocks
    private ClientesPontoColetaService service;

    @Test
    void listarColetoresDisponiveis_deveRetornarListaDeColetores() {
        PerfilUsuario coletor1 = new PerfilUsuario();
        coletor1.setId(1L);
        coletor1.setNome("Coletor 1");

        PerfilUsuario coletor2 = new PerfilUsuario();
        coletor2.setId(2L);
        coletor2.setNome("Coletor 2");

        when(perfilUsuarioRepository.findAllByTipoUsuario(TipoUsuario.COLETOR))
            .thenReturn(List.of(coletor1, coletor2));

        List<ColetorDisponivelDTO> resultado = service.listarColetoresDisponiveis();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Coletor 1", resultado.get(0).getNome());
        assertEquals("Coletor 2", resultado.get(1).getNome());
    }

    @Test
    void listarColetoresDisponiveis_deveRetornarListaVaziaSeNaoHouverColetores() {
        when(perfilUsuarioRepository.findAllByTipoUsuario(TipoUsuario.COLETOR))
            .thenReturn(Collections.emptyList());

        List<ColetorDisponivelDTO> resultado = service.listarColetoresDisponiveis();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}
