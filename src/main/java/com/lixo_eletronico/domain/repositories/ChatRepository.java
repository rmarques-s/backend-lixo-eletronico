package com.lixo_eletronico.domain.repositories;


import com.lixo_eletronico.domain.entities.Chat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	Optional<Chat> findByCliente_IdAndServico_Id(Long clienteId, Long servicoId);
}