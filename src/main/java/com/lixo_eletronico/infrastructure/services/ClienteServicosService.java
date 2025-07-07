package com.lixo_eletronico.infrastructure.services;


import com.lixo_eletronico.domain.entities.*;
import com.lixo_eletronico.domain.enums.StatusServico;
import com.lixo_eletronico.domain.repositories.*;
import com.lixo_eletronico.shared.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServicosService {

    private final ServicoRepository servicoRepository;
    private final PerfilUsuarioRepository perfilRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ChatRepository chatRepository;

    public List<ServicoResponseDTO> listarServicosDisponiveis() {
        return servicoRepository.findAll().stream()
                .filter(servico -> servico.getStatus() == StatusServico.ATIVO)
                .map(ServicoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ChatResponseDTO iniciarChat(Long servicoId, String clienteKeycloakId) {
        var servico = servicoRepository.findById(servicoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        var cliente = perfilRepository.findByIdKeycloak(clienteKeycloakId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado"));

        PerfilUsuario empresa = servico.getEmpresa();

        // Verifica se já existe um chat entre este cliente e serviço
        Optional<Chat> chatExistente = chatRepository.findAll().stream()
            .filter(c -> c.getCliente().equals(cliente) &&
                         c.getServico().equals(servico))
            .findFirst();

        if (chatExistente.isPresent()) {
            return new ChatResponseDTO(chatExistente.get().getId());
        }

        Chat chat = new Chat();
        chat.setCliente(cliente);
        chat.setEmpresa(empresa);
        chat.setServico(servico);

        chatRepository.save(chat);

        return new ChatResponseDTO(chat.getId());
    }


    public void agendarServico(Long servicoId, String clienteId, AgendamentoRequestDTO dto) {
        var servico = servicoRepository.findById(servicoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        var cliente = perfilRepository.findByIdKeycloak(clienteId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado"));

        boolean jaAgendado = agendamentoRepository
            .findByServicoIdAndCliente_IdKeycloakAndCanceladoFalse(servicoId, clienteId)
            .isPresent();

        if (jaAgendado) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já agendou este serviço.");
        }

        if (dto.getDataAgendada() == null || dto.getDataAgendada().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de agendamento deve ser futura.");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setServico(servico);
        agendamento.setCliente(cliente);
        agendamento.setDataAgendada(dto.getDataAgendada());
        agendamento.setCancelado(false);

        agendamentoRepository.save(agendamento);
    }

    public void cancelarAgendamento(Long servicoId, String clienteId) {
        var agendamento = agendamentoRepository
            .findByServicoIdAndCliente_IdKeycloakAndCanceladoFalse(servicoId, clienteId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        agendamento.setCancelado(true);
        agendamentoRepository.save(agendamento);
    }

    public void avaliarServico(Long servicoId, String clienteId, AvaliacaoServicoDTO dto) {
        var servico = servicoRepository.findById(servicoId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        var cliente = perfilRepository.findByIdKeycloak(clienteId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado"));

        boolean jaAvaliou = avaliacaoRepository.existsByServicoIdAndCliente_IdKeycloak(servicoId, clienteId);
        if (jaAvaliou) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já avaliou este serviço.");
        }

        if (dto.getNota() < 1 || dto.getNota() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota deve ser entre 1 e 5.");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setServico(servico);
        avaliacao.setCliente(cliente);
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        avaliacaoRepository.save(avaliacao);
    }
}
