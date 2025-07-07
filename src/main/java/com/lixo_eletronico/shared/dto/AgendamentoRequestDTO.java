package com.lixo_eletronico.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonProperty("data_agendada")
    private LocalDateTime dataAgendada;
}