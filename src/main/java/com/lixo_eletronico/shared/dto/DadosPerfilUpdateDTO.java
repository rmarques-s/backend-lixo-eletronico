package com.lixo_eletronico.shared.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lixo_eletronico.domain.enums.TipoUsuario;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DadosPerfilUpdateDTO {
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

    private EnderecoDTO endereco;

    @Getter
    @Setter
    public static class EnderecoDTO {
        private String cep;
        private String rua;
        private String numero;
        private String complemento;
        private String bairro;
        private String cidade;
        private String estado;
    }
}