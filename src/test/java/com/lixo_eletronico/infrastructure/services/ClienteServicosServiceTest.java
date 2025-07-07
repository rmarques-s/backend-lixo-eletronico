package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.entities.*;
import com.lixo_eletronico.domain.enums.StatusServico;
import com.lixo_eletronico.domain.repositories.*;
import com.lixo_eletronico.shared.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServicosServiceTest {

    @Mock private ServicoRepository servicoRepository;
    @Mock private PerfilUsuarioRepository perfilRepository;
    @Mock private AgendamentoRepository agendamentoRepository;
    @Mock private AvaliacaoRepository avaliacaoRepository;
    @Mock private ChatRepository chatRepository;

    @InjectMocks private ClienteServicosService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ========== TESTE: LISTAGEM DE SERVIÇOS ==========
    @Test
    void listarServicosDisponiveis_deveRetornarSomenteAtivos() {
        Servico s1 = new Servico(); 
        s1.setStatus(StatusServico.ATIVO);
        PerfilUsuario empresa = new PerfilUsuario();
        empresa.setNome("Empresa X");
        s1.setEmpresa(empresa);

        Servico s2 = new Servico(); 
        s2.setStatus(StatusServico.CONCLUIDO);

        when(servicoRepository.findAll()).thenReturn(List.of(s1, s2));

        var result = service.listarServicosDisponiveis();
        assertEquals(1, result.size());
        assertEquals("Empresa X", result.get(0).getEmpresaNome());
    }


    // ========== TESTE: INICIAR CHAT ==========
    @Test
    void iniciarChat_deveCriarChatSeNaoExistir() {
        Servico servico = new Servico();
        servico.setId(1L);
        PerfilUsuario empresa = new PerfilUsuario();
        empresa.setId(2L);
        servico.setEmpresa(empresa);

        PerfilUsuario cliente = new PerfilUsuario();
        cliente.setId(3L);

        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(perfilRepository.findByIdKeycloak("cliente123")).thenReturn(Optional.of(cliente));
        when(chatRepository.findAll()).thenReturn(List.of());

        when(chatRepository.save(any(Chat.class)))
            .thenAnswer(invocation -> {
                Chat chat = invocation.getArgument(0);
                chat.setId(99L); // simula ID atribuído pelo banco
                return chat;
            });

        var response = service.iniciarChat(1L, "cliente123");
        assertEquals(99L, response.getChatId());
    }


    @Test
    void iniciarChat_erroServicoNaoEncontrado() {
        when(servicoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.iniciarChat(1L, "clienteId"));
    }

    // ========== TESTE: AGENDAR SERVIÇO ==========
    @Test
    void agendarServico_deveAgendarComSucesso() {
        var servico = new Servico(); servico.setId(1L);
        var cliente = new PerfilUsuario(); cliente.setId(2L);
        var dto = new AgendamentoRequestDTO();
        dto.setDataAgendada(LocalDateTime.now().plusDays(1));

        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(perfilRepository.findByIdKeycloak("clienteId")).thenReturn(Optional.of(cliente));
        when(agendamentoRepository.findByServicoIdAndCliente_IdKeycloakAndCanceladoFalse(1L, "clienteId"))
            .thenReturn(Optional.empty());

        service.agendarServico(1L, "clienteId", dto);
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }

    @Test
    void agendarServico_erroDataPassada() {
        var servico = new Servico(); servico.setId(1L);
        var cliente = new PerfilUsuario(); cliente.setId(2L);
        var dto = new AgendamentoRequestDTO();
        dto.setDataAgendada(LocalDateTime.now().minusDays(1));

        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(perfilRepository.findByIdKeycloak("clienteId")).thenReturn(Optional.of(cliente));

        assertThrows(ResponseStatusException.class, () ->
                service.agendarServico(1L, "clienteId", dto));
    }

    // ========== TESTE: CANCELAR AGENDAMENTO ==========
    @Test
    void cancelarAgendamento_deveCancelarComSucesso() {
        Agendamento agendamento = new Agendamento();
        agendamento.setCancelado(false);

        when(agendamentoRepository.findByServicoIdAndCliente_IdKeycloakAndCanceladoFalse(1L, "clienteId"))
            .thenReturn(Optional.of(agendamento));

        service.cancelarAgendamento(1L, "clienteId");
        assertTrue(agendamento.getCancelado());
    }

    @Test
    void cancelarAgendamento_erroNaoEncontrado() {
        when(agendamentoRepository.findByServicoIdAndCliente_IdKeycloakAndCanceladoFalse(1L, "clienteId"))
            .thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                service.cancelarAgendamento(1L, "clienteId"));
    }

    // ========== TESTE: AVALIAR SERVIÇO ==========
    @Test
    void avaliarServico_deveSalvarComSucesso() {
        var servico = new Servico(); servico.setId(1L);
        var cliente = new PerfilUsuario(); cliente.setId(2L);
        var dto = new AvaliacaoServicoDTO(); dto.setNota(5); dto.setComentario("bom");

        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(perfilRepository.findByIdKeycloak("clienteId")).thenReturn(Optional.of(cliente));
        when(avaliacaoRepository.existsByServicoIdAndCliente_IdKeycloak(1L, "clienteId")).thenReturn(false);

        service.avaliarServico(1L, "clienteId", dto);
        verify(avaliacaoRepository).save(any(Avaliacao.class));
    }

    @Test
    void avaliarServico_erroNotaInvalida() {
        var servico = new Servico(); servico.setId(1L);
        var cliente = new PerfilUsuario(); cliente.setId(2L);
        var dto = new AvaliacaoServicoDTO(); dto.setNota(10);

        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(perfilRepository.findByIdKeycloak("clienteId")).thenReturn(Optional.of(cliente));
        when(avaliacaoRepository.existsByServicoIdAndCliente_IdKeycloak(1L, "clienteId")).thenReturn(false);

        assertThrows(ResponseStatusException.class, () ->
                service.avaliarServico(1L, "clienteId", dto));
    }

    @Test
    void avaliarServico_erroJaAvaliou() {
        var servico = new Servico(); servico.setId(1L);
        var cliente = new PerfilUsuario(); cliente.setId(2L);
        var dto = new AvaliacaoServicoDTO(); dto.setNota(3);

        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(perfilRepository.findByIdKeycloak("clienteId")).thenReturn(Optional.of(cliente));
        when(avaliacaoRepository.existsByServicoIdAndCliente_IdKeycloak(1L, "clienteId")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () ->
                service.avaliarServico(1L, "clienteId", dto));
    }
}
