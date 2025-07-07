package com.lixo_eletronico.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Servico servico;

    @ManyToOne(optional = false)
    private PerfilUsuario cliente;

    private LocalDateTime dataAgendada;

    private Boolean cancelado = false;
}
