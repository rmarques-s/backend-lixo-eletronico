package com.lixo_eletronico.shared.dto;

import java.time.LocalDateTime;

import com.lixo_eletronico.domain.entities.Coleta;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColetaResponseDTO {
    private Long id;
    private String item;
    private String coletorNome;
    private LocalDateTime agendadaPara;
    private boolean concluida;

    public ColetaResponseDTO(Coleta c) {
        this.id = c.getId();
        this.item = c.getItem();
        this.coletorNome = c.getColetor().getNome();
        this.agendadaPara = c.getAgendadaPara();
        this.concluida = c.isConcluida();
    }
}