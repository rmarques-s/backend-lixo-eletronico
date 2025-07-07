package com.lixo_eletronico.shared.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lixo_eletronico.domain.entities.Coleta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColetaHistoricoDTO {
    private Long id;
    private String item;
    private LocalDateTime agendadaPara;
    private String nomeColetor;

    public ColetaHistoricoDTO(Coleta coleta) {
        this.id = coleta.getId();
        this.item = coleta.getItem();
        this.agendadaPara = coleta.getAgendadaPara();
        this.nomeColetor = coleta.getColetor().getNome();
    }
}