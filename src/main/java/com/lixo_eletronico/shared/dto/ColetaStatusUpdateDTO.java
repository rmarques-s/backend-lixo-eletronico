package com.lixo_eletronico.shared.dto;

import com.lixo_eletronico.domain.enums.StatusColeta;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ColetaStatusUpdateDTO {
    private StatusColeta status;
}
