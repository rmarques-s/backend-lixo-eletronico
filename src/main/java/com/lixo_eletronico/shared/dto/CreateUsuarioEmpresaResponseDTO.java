package com.lixo_eletronico.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsuarioEmpresaResponseDTO {
	@JsonProperty("nome_empresa")
	private String nomeEmpresa;
	
	@JsonProperty("email")
	private String email;

	private String mensagem;

	private String role;
}
