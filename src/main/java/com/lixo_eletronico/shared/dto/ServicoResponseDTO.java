package com.lixo_eletronico.shared.dto;

import com.lixo_eletronico.domain.entities.Servico;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Boolean status;
    private String empresaNome;

    public ServicoResponseDTO(Servico servico) {
        this.id = servico.getId();
        this.titulo = servico.getTitulo();
        this.descricao = servico.getDescricao();
        this.status = servico.getStatus();
        this.empresaNome = servico.getEmpresa().getNome();
    }
}
