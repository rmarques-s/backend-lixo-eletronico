package com.lixo_eletronico.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoColetaDTO {
    private Integer nota;
    private String comentario;
}
