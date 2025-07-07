package com.lixo_eletronico.domain.repositories;

import com.lixo_eletronico.domain.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findAllByEmpresa_IdKeycloak(String idKeycloak);
    Optional<Servico> findByIdAndEmpresa_IdKeycloak(Long id, String idKeycloak);
}