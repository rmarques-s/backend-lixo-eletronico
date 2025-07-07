package com.lixo_eletronico.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lixo_eletronico.domain.entities.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    boolean existsByServicoIdAndCliente_IdKeycloak(Long servicoId, String keycloakId);
}