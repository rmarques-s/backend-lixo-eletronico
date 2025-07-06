package com.lixo_eletronico.infrastructure.usecases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UsuarioData {
	private String role;
	private String email;
	private String senha;
	private String nome;
	private boolean criado;
	private String motivoErroCriacao;

	public abstract UserRepresentation build();
}
