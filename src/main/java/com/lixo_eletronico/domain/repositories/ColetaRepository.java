package com.lixo_eletronico.domain.repositories;

import com.lixo_eletronico.domain.entities.Coleta;
import com.lixo_eletronico.domain.enums.StatusColeta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColetaRepository extends JpaRepository<Coleta, Long> {
    List<Coleta> findAllByCliente_IdKeycloakAndConcluidaFalse(String clienteId);
    List<Coleta> findAllByColetor_IdKeycloak(String coletorId);
    List<Coleta> findAllByCliente_IdKeycloakAndStatus(String keycloakId, StatusColeta status);
    List<Coleta> findAllByCliente_IdKeycloak(String keycloakId);
}