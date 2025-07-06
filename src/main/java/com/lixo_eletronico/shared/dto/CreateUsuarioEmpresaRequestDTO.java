package com.lixo_eletronico.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsuarioEmpresaRequestDTO {
	@NotBlank
	@Size(min = 2, max = 50)
	private String nome;

	@NotBlank
	private String cnpj;
	
	@Email
	@NotBlank
	private String email;

	@NotBlank
	@Size(min = 6, max = 100)
	private String senha;
}
