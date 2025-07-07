package com.lixo_eletronico.shared.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColetaAgendamentoDTO {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonProperty("agendada_para")
    private LocalDateTime agendadaPara;
}