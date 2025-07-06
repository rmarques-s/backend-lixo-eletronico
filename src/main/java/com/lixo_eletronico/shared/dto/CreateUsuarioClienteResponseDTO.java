package com.lixo_eletronico.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsuarioClienteResponseDTO {

    @JsonProperty("primeiro_nome")
    private String primeiroNome;

    private String sobrenome;
    
    @JsonProperty("email")
    private String email;

    private String mensagem;
    
    private String role;
}
