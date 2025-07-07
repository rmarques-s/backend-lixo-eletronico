package com.lixo_eletronico.shared.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lixo_eletronico.domain.entities.Coleta;
import com.lixo_eletronico.domain.enums.StatusColeta;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ColetaDetalheDTO {
    private Long id;
    private String item;
    
    @JsonProperty("cliente_nome")
    private String clienteNome;
    
    @JsonProperty("agendado_para")
    private LocalDateTime agendadaPara;
    
    private StatusColeta status;

    public ColetaDetalheDTO(Coleta c) {
        this.id = c.getId();
        this.item = c.getItem();
        this.clienteNome = c.getCliente().getNome();
        this.agendadaPara = c.getAgendadaPara();
        this.status = c.getStatus();
    }
}
