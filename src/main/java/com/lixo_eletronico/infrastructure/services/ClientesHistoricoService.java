package com.lixo_eletronico.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lixo_eletronico.domain.enums.StatusColeta;
import com.lixo_eletronico.domain.repositories.AgendamentoRepository;
import com.lixo_eletronico.domain.repositories.ColetaRepository;
import com.lixo_eletronico.shared.dto.ColetaHistoricoDTO;
import com.lixo_eletronico.shared.dto.ServicoHistoricoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientesHistoricoService {

 private final ColetaRepository coletaRepository;
 private final AgendamentoRepository agendamentoRepository;

 public List<ColetaHistoricoDTO> listarColetasFinalizadas(String clienteKeycloakId) {
     return coletaRepository.findAllByCliente_IdKeycloakAndStatus(clienteKeycloakId, StatusColeta.REALIZADA)
             .stream()
             .map(ColetaHistoricoDTO::new)
             .collect(Collectors.toList());
 }

 public List<ServicoHistoricoDTO> listarServicosUtilizados(String clienteKeycloakId) {
     return agendamentoRepository.findAllByCliente_IdKeycloakAndCanceladoFalse(clienteKeycloakId)
             .stream()
             .map(ServicoHistoricoDTO::new)
             .collect(Collectors.toList());
 }
}
