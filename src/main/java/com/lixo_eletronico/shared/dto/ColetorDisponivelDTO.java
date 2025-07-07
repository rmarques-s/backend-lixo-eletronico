package com.lixo_eletronico.shared.dto;

import com.lixo_eletronico.domain.entities.PerfilUsuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColetorDisponivelDTO {
    private Long id;
    private String nome;
    private String email;
    private String celular;
    private String cidade;

    public ColetorDisponivelDTO(PerfilUsuario perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
        this.email = perfil.getEmail();
        this.celular = perfil.getCelular();
        this.cidade = perfil.getEndereco() != null ? perfil.getEndereco().getCidade() : null;
    }
}