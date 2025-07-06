package com.lixo_eletronico.shared.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lixo_eletronico.domain.entities.Endereco;
import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosPerfilResponseDTO {

    private Long id;
    private String nome;
    private String sobrenome;
    @JsonIgnore
    private String email;
    private String cpf;
    private String cnpj;
    private String celular;

    @JsonProperty("data_nascimento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @JsonProperty("tipo_usuario")
    private TipoUsuario tipoUsuario;

    private EnderecoResponseDTO endereco;

    public DadosPerfilResponseDTO(PerfilUsuario perfil) {
        this.id = perfil.getId();
        this.nome = perfil.getNome();
        this.sobrenome = perfil.getSobrenome();
        this.email = perfil.getEmail();
        this.cpf = perfil.getCpf();
        this.cnpj = perfil.getCnpj();
        this.celular = perfil.getCelular();
        this.dataNascimento = perfil.getDataNascimento();
        this.tipoUsuario = perfil.getTipoUsuario();

        if (perfil.getEndereco() != null) {
            this.endereco = new EnderecoResponseDTO(perfil.getEndereco());
        }
    }
}