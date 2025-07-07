package com.lixo_eletronico.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lixo_eletronico.domain.repositories.ColetaRepository;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.shared.dto.ColetaDetalheDTO;
import com.lixo_eletronico.shared.dto.ColetaStatusUpdateDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColetasColetorService {

    private final ColetaRepository coletaRepository;

    public List<ColetaDetalheDTO> listarPorColetor(String keycloakId) {
        return coletaRepository.findAllByColetor_IdKeycloak(keycloakId)
                .stream()
                .map(ColetaDetalheDTO::new)
                .collect(Collectors.toList());
    }

    public void atualizarStatus(String keycloakId, Long coletaId, ColetaStatusUpdateDTO dto) {
        var coleta = coletaRepository.findById(coletaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coleta não encontrada"));

        if (!coleta.getColetor().getIdKeycloak().equals(keycloakId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Essa coleta não pertence a esse coletor");
        }

        coleta.setStatus(dto.getStatus());
        coletaRepository.save(coleta);
    }
}
