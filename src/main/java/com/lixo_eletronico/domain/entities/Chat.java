package com.lixo_eletronico.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private PerfilUsuario cliente;

    @ManyToOne(optional = false)
    private PerfilUsuario empresa;

    @ManyToOne(optional = false)
    private Servico servico;

    private LocalDateTime criadoEm = LocalDateTime.now();
}
