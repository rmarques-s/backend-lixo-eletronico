package com.lixo_eletronico.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lixo_eletronico.domain.entities.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findByServicoIdAndCliente_IdKeycloakAndCanceladoFalse(Long servicoId, String keycloakId);
}
