package com.lixo_eletronico.infrastructure.usecases;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.enums.TipoUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.infrastructure.services.KeycloakAdminService;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;

import lombok.RequiredArgsConstructor;

@Component("createEmpresaHandler")
@RequiredArgsConstructor
public class CreateEmpresaCommandHandler implements ICreateUsuario {

	private final KeycloakAdminService keycloakService;
	private final PerfilUsuarioRepository perfilRepository;

	public UsuarioData execute(UsuarioData data) {
		UsuarioEmpresaData createdData;
		try {
			createdData = (UsuarioEmpresaData) keycloakService.criarUsuario(data);
			
			PerfilUsuario perfil = new PerfilUsuario();
			perfil.setIdKeycloak(createdData.getKeycloakId());
			perfil.setEmail(createdData.getEmail());
			perfil.setNome(createdData.getNome());
			perfil.setTipoUsuario(TipoUsuario.EMPRESA);
			perfil.setCnpj(createdData.getCnpj());
		
			this.perfilRepository.save(perfil);
		} catch (Exception e) {
			throw e;
		}
		
		return createdData;

	}
}