package com.lixo_eletronico.shared.dto;

import com.lixo_eletronico.domain.enums.StatusServico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtualizarStatusServicoDTO {
    private StatusServico status;
}
