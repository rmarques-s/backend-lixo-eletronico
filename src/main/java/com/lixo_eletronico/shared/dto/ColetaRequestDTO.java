package com.lixo_eletronico.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColetaRequestDTO {
    private String item;
   
    @JsonProperty("coletor_id")
    private Long coletorId;
}