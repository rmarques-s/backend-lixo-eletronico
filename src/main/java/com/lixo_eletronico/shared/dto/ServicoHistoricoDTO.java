package com.lixo_eletronico.shared.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lixo_eletronico.domain.entities.Agendamento;
import com.lixo_eletronico.domain.entities.Servico;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoHistoricoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    @JsonProperty("nome_empresa")
    private String nomeEmpresa;
    @JsonProperty("data_agendada")
    private LocalDateTime dataAgendada;

    public ServicoHistoricoDTO(Agendamento agendamento) {
        this.id = agendamento.getServico().getId();
        this.titulo = agendamento.getServico().getTitulo();
        this.descricao = agendamento.getServico().getDescricao();
        this.nomeEmpresa = agendamento.getServico().getEmpresa().getNome();
        this.dataAgendada = agendamento.getDataAgendada();
    }
}