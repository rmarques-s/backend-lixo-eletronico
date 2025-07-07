package com.lixo_eletronico.shared.dto;

import com.lixo_eletronico.domain.enums.StatusServico;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusServicoDTO {
    private StatusServico status;
}
