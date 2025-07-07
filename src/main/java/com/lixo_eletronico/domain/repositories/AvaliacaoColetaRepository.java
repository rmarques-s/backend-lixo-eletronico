package com.lixo_eletronico.domain.repositories;

import com.lixo_eletronico.domain.entities.AvaliacaoColeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoColetaRepository extends JpaRepository<AvaliacaoColeta, Long> {
    boolean existsByColetaIdAndCliente_IdKeycloak(Long coletaId, String clienteId);
}