package com.lixo_eletronico.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Servico servico;

    @ManyToOne(optional = false)
    private PerfilUsuario cliente;

    private Integer nota;

    private String comentario;
}
