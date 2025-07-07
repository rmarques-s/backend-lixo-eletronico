package com.lixo_eletronico.infrastructure.services;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import com.lixo_eletronico.domain.entities.Agendamento;
import com.lixo_eletronico.domain.entities.Coleta;
import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.entities.Servico;
import com.lixo_eletronico.domain.enums.StatusColeta;
import com.lixo_eletronico.domain.repositories.AgendamentoRepository;
import com.lixo_eletronico.domain.repositories.ColetaRepository;
import com.lixo_eletronico.shared.dto.ColetaHistoricoDTO;
import com.lixo_eletronico.shared.dto.ServicoHistoricoDTO;

@ExtendWith(MockitoExtension.class)
public class ClientesHistoricoServiceTest {

    @Mock
    private ColetaRepository coletaRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private ClientesHistoricoService service;

    @Test
    void listarColetasFinalizadas_deveRetornarLista() {
        String clienteId = "abc123";

        // Cria um mock de coletor
        PerfilUsuario coletor = new PerfilUsuario();
        coletor.setNome("João Coletor");

        // Cria uma coleta com o coletor preenchido
        Coleta coleta = new Coleta();
        coleta.setId(1L);
        coleta.setItem("TV velha");
        coleta.setAgendadaPara(LocalDateTime.now().plusDays(1));
        coleta.setStatus(StatusColeta.REALIZADA);
        coleta.setColetor(coletor);

        List<Coleta> coletas = List.of(coleta);
        when(coletaRepository.findAllByCliente_IdKeycloakAndStatus(clienteId, StatusColeta.REALIZADA))
            .thenReturn(coletas);

        List<ColetaHistoricoDTO> resultado = service.listarColetasFinalizadas(clienteId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Coletor", resultado.get(0).getNomeColetor());
    }


    @Test
    void listarColetasFinalizadas_deveRetornarListaVaziaQuandoNaoHouverColetas() {
        when(coletaRepository.findAllByCliente_IdKeycloakAndStatus(anyString(), eq(StatusColeta.REALIZADA)))
            .thenReturn(Collections.emptyList());

        var resultado = service.listarColetasFinalizadas("cliente");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void listarServicosUtilizados_deveRetornarLista() {
        String clienteId = "xyz789";

        // Mock da empresa
        PerfilUsuario empresa = new PerfilUsuario();
        empresa.setNome("Empresa Tech");

        // Mock do serviço
        Servico servico = new Servico();
        servico.setId(1L);
        servico.setTitulo("Serviço X");
        servico.setDescricao("Descrição X");
        servico.setEmpresa(empresa); // ESSENCIAL

        // Mock do agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setServico(servico);
        agendamento.setCancelado(false);

        List<Agendamento> agendamentos = List.of(agendamento);
        when(agendamentoRepository.findAllByCliente_IdKeycloakAndCanceladoFalse(clienteId))
            .thenReturn(agendamentos);

        List<ServicoHistoricoDTO> resultado = service.listarServicosUtilizados(clienteId);

        assertEquals(1, resultado.size());
        assertEquals("Serviço X", resultado.get(0).getTitulo());
        assertEquals("Descrição X", resultado.get(0).getDescricao());
        assertEquals("Empresa Tech", resultado.get(0).getNomeEmpresa());
    }

}
