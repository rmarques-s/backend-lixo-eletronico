package com.lixo_eletronico.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColetaRequestDTO {
    private String item;
   
    @JsonProperty("coletor_id")
    private Long coletorId;
}