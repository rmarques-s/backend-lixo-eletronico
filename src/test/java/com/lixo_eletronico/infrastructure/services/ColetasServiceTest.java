package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.entities.*;
import com.lixo_eletronico.domain.repositories.*;
import com.lixo_eletronico.shared.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColetasServiceTest {

    @Mock private ColetaRepository coletaRepository;
    @Mock private PerfilUsuarioRepository perfilRepository;
    @Mock private AvaliacaoColetaRepository avaliacaoRepository;

    @InjectMocks private ColetasService service;

    @Test
    void solicitarColeta_sucesso() {
        var cliente = new PerfilUsuario();
        var coletor = new PerfilUsuario();
        var dto = new ColetaRequestDTO("Notebook", 1L);

        when(perfilRepository.findByIdKeycloak("abc")).thenReturn(Optional.of(cliente));
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(coletor));

        assertDoesNotThrow(() -> service.solicitarColeta("abc", dto));
        verify(coletaRepository).save(any());
    }

    @Test
    void solicitarColeta_clienteNaoEncontrado() {
        when(perfilRepository.findByIdKeycloak("abc")).thenReturn(Optional.empty());
        var dto = new ColetaRequestDTO("Item", 1L);

        assertThrows(ResponseStatusException.class, () -> service.solicitarColeta("abc", dto));
    }

    @Test
    void listarColetasEmAberto_sucesso() {
        // Criação do cliente e coletor
        PerfilUsuario cliente = new PerfilUsuario();
        cliente.setIdKeycloak("cliente-key");

        PerfilUsuario coletor = new PerfilUsuario();
        coletor.setNome("Coletor X");

        // Criação da coleta com cliente e coletor definidos
        Coleta coleta = new Coleta();
        coleta.setCliente(cliente);
        coleta.setColetor(coletor);
        coleta.setItem("Notebook");

        when(coletaRepository.findAllByCliente_IdKeycloakAndConcluidaFalse("cliente-key"))
            .thenReturn(List.of(coleta));

        List<ColetaResponseDTO> resultado = service.listarColetasEmAberto("cliente-key");

        assertEquals(1, resultado.size());
        assertEquals("Notebook", resultado.get(0).getItem());
        assertEquals("Coletor X", resultado.get(0).getColetorNome());
    }
    
    @Test
    void agendarColeta_sucesso() {
        var coleta = new Coleta();
        var cliente = new PerfilUsuario(); cliente.setIdKeycloak("abc");
        coleta.setCliente(cliente);

        when(coletaRepository.findById(1L)).thenReturn(Optional.of(coleta));
        service.agendarColeta("abc", 1L, new ColetaAgendamentoDTO(LocalDateTime.now().plusDays(1)));

        verify(coletaRepository).save(coleta);
    }

    @Test
    void agendarColeta_coletaNaoEncontrada() {
        when(coletaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                service.agendarColeta("abc", 1L, new ColetaAgendamentoDTO(LocalDateTime.now())));
    }

    @Test
    void concluirColeta_sucesso() {
        var coleta = new Coleta();
        var cliente = new PerfilUsuario(); cliente.setIdKeycloak("abc");
        coleta.setCliente(cliente);

        when(coletaRepository.findById(1L)).thenReturn(Optional.of(coleta));
        service.concluirColeta("abc", 1L);

        verify(coletaRepository).save(coleta);
        assertTrue(coleta.isConcluida());
    }

    @Test
    void avaliarColeta_sucesso() {
        var coleta = new Coleta();
        var cliente = new PerfilUsuario(); cliente.setIdKeycloak("abc");
        coleta.setCliente(cliente);

        var dto = new AvaliacaoColetaDTO(5, "ótimo serviço");

        when(coletaRepository.findById(1L)).thenReturn(Optional.of(coleta));
        when(avaliacaoRepository.existsByColetaIdAndCliente_IdKeycloak(1L, "abc")).thenReturn(false);

        assertDoesNotThrow(() -> service.avaliarColeta("abc", 1L, dto));
        verify(avaliacaoRepository).save(any());
    }

    @Test
    void avaliarColeta_jaAvaliado() {
        var coleta = new Coleta();
        var cliente = new PerfilUsuario(); cliente.setIdKeycloak("abc");
        coleta.setCliente(cliente);

        when(coletaRepository.findById(1L)).thenReturn(Optional.of(coleta));
        when(avaliacaoRepository.existsByColetaIdAndCliente_IdKeycloak(1L, "abc")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.avaliarColeta("abc", 1L, new AvaliacaoColetaDTO(4, "")));
    }
} 
