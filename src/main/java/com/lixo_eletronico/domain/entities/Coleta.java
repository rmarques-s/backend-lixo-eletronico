package com.lixo_eletronico.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.lixo_eletronico.domain.enums.StatusColeta;

@Entity
@Getter
@Setter
public class Coleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private PerfilUsuario cliente;

    @ManyToOne(optional = false)
    private PerfilUsuario coletor;

    private String item;

    private LocalDateTime solicitadaEm = LocalDateTime.now();

    private LocalDateTime agendadaPara;

    private boolean concluida = false;

    @Enumerated(EnumType.STRING)
    private StatusColeta status = StatusColeta.PENDENTE;
}

