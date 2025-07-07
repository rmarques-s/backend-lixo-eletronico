package com.lixo_eletronico.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AvaliacaoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Coleta coleta;

    @ManyToOne(optional = false)
    private PerfilUsuario cliente;

    private Integer nota;

    private String comentario;
}