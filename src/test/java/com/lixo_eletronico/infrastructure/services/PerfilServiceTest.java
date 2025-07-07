package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.entities.Endereco;
import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.shared.dto.DadosPerfilResponseDTO;
import com.lixo_eletronico.shared.dto.DadosPerfilUpdateDTO;
import com.lixo_eletronico.shared.dto.DadosPerfilUpdateDTO.EnderecoDTO;
import com.lixo_eletronico.shared.dto.EnderecoResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerfilServiceTest {

    @Mock
    private PerfilUsuarioRepository perfilRepository;

    @InjectMocks
    private PerfilService perfilService;

    private PerfilUsuario perfil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        perfil = new PerfilUsuario();
        perfil.setId(1L);
        perfil.setNome("João");
        perfil.setEmail("joao@email.com");
        perfil.setCpf("12345678900");
    }

    @Test
    void buscarPerfilUsuario_deveRetornarDadosPerfil_quandoEncontrado() {
        when(perfilRepository.findByIdKeycloak("abc123")).thenReturn(Optional.of(perfil));

        DadosPerfilResponseDTO result = perfilService.buscarPerfilUsuario("abc123");

        assertNotNull(result);
        assertEquals("João", result.getNome());
        verify(perfilRepository).findByIdKeycloak("abc123");
    }

    @Test
    void buscarPerfilUsuario_deveLancarExcecao_quandoNaoEncontrado() {
        when(perfilRepository.findByIdKeycloak("invalido")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> perfilService.buscarPerfilUsuario("invalido"));
    }

    @Test
    void atualizarPerfil_deveAtualizarDados_quandoValido() {
        DadosPerfilUpdateDTO dto = new DadosPerfilUpdateDTO();
        dto.setNome("Maria");
        dto.setCpf("98765432100");
        dto.setDataNascimento(LocalDate.of(2000, 1, 1));

        EnderecoDTO  enderecoDTO = new EnderecoDTO();
        enderecoDTO.setCep("12345-678");
        enderecoDTO.setCidade("Salvador");
        dto.setEndereco(enderecoDTO);

        when(perfilRepository.findByIdKeycloak("abc123")).thenReturn(Optional.of(perfil));

        DadosPerfilResponseDTO result = perfilService.atualizarPerfil("abc123", dto);

        assertNotNull(result);
        assertEquals("Maria", result.getNome());
        assertEquals("98765432100", result.getCpf());
        assertEquals("Salvador", result.getEndereco().getCidade());

        verify(perfilRepository).save(perfil);
    }

    @Test
    void atualizarPerfil_deveLancarExcecao_quandoUsuarioNaoEncontrado() {
        when(perfilRepository.findByIdKeycloak("nao_existe")).thenReturn(Optional.empty());

        DadosPerfilUpdateDTO dto = new DadosPerfilUpdateDTO();
        dto.setNome("Novo Nome");

        assertThrows(ResponseStatusException.class, () -> perfilService.atualizarPerfil("nao_existe", dto));
    }
}
