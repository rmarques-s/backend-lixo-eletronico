package com.lixo_eletronico.infrastructure.usecases;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.lixo_eletronico.infrastructure.services.KeycloakAdminService;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;

import lombok.RequiredArgsConstructor;

@Component("createEmpresaHandler")
@RequiredArgsConstructor
public class CreateEmpresaCommandHandler implements ICreateUsuario {

	private final KeycloakAdminService keycloakService;

	public UsuarioData execute(UsuarioData data) {
		try {
			keycloakService.criarUsuario(data);
			data.setCriado(true);
		} catch (Exception e) {
			throw e;
		}
		
		return data;

	}
}