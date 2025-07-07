package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.entities.*;
import com.lixo_eletronico.domain.repositories.*;
import com.lixo_eletronico.shared.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColetasService {

    private final ColetaRepository coletaRepository;
    private final PerfilUsuarioRepository perfilRepository;
    private final AvaliacaoColetaRepository avaliacaoRepository;

    public void solicitarColeta(String clienteKeycloakId, ColetaRequestDTO dto) {
        var cliente = perfilRepository.findByIdKeycloak(clienteKeycloakId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado"));

        var coletor = perfilRepository.findById(dto.getColetorId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coletor não encontrado"));

        var coleta = new Coleta();
        coleta.setCliente(cliente);
        coleta.setColetor(coletor);
        coleta.setItem(dto.getItem());

        coletaRepository.save(coleta);
    }

    public List<ColetaResponseDTO> listarColetasEmAberto(String clienteKeycloakId) {
        return coletaRepository.findAllByCliente_IdKeycloakAndConcluidaFalse(clienteKeycloakId).stream()
                .map(ColetaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void agendarColeta(String clienteKeycloakId, Long coletaId, ColetaAgendamentoDTO dto) {
        var coleta = coletaRepository.findById(coletaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleta não encontrada"));

        if (!coleta.getCliente().getIdKeycloak().equals(clienteKeycloakId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Coleta não pertence ao cliente");
        }

        coleta.setAgendadaPara(dto.getAgendadaPara());
        coletaRepository.save(coleta);
    }

    public void concluirColeta(String clienteKeycloakId, Long coletaId) {
        var coleta = coletaRepository.findById(coletaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleta não encontrada"));

        if (!coleta.getCliente().getIdKeycloak().equals(clienteKeycloakId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Coleta não pertence ao cliente");
        }

        coleta.setConcluida(true);
        coletaRepository.save(coleta);
    }

    public void avaliarColeta(String clienteKeycloakId, Long coletaId, AvaliacaoColetaDTO dto) {
        var coleta = coletaRepository.findById(coletaId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleta não encontrada"));

        if (!coleta.getCliente().getIdKeycloak().equals(clienteKeycloakId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Coleta não pertence ao cliente" );
        }

        if (avaliacaoRepository.existsByColetaIdAndCliente_IdKeycloak(coletaId, clienteKeycloakId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já avaliou essa coleta");
        }

        var avaliacao = new AvaliacaoColeta();
        avaliacao.setColeta(coleta);
        avaliacao.setCliente(coleta.getCliente());
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        avaliacaoRepository.save(avaliacao);
    }
}