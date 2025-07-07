package com.lixo_eletronico.domain.entities;

import com.lixo_eletronico.domain.enums.StatusServico;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status_novo")
    private StatusServico status = StatusServico.ATIVO;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private PerfilUsuario empresa;
}
