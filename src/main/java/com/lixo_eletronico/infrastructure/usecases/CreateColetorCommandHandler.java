package com.lixo_eletronico.infrastructure.usecases;

import org.springframework.stereotype.Component;

import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.enums.TipoUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.infrastructure.services.KeycloakAdminService;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;

import lombok.RequiredArgsConstructor;

@Component("createColetorHandler")
@RequiredArgsConstructor
public class CreateColetorCommandHandler implements ICreateUsuario {

	private final KeycloakAdminService keycloakService;
	private final PerfilUsuarioRepository perfilRepository;

	public UsuarioData execute(UsuarioData data) {
		UsuarioColetorData createdData;
		try {
			createdData = (UsuarioColetorData) keycloakService.criarUsuario(data);
			
			PerfilUsuario perfil = new PerfilUsuario();
			perfil.setIdKeycloak(createdData.getKeycloakId());
			perfil.setEmail(createdData.getEmail());
			perfil.setNome(createdData.getNome());
			perfil.setSobrenome(createdData.getSobrenome());
			perfil.setCnpj(createdData.getCnpj());
			perfil.setCpf(createdData.getCpf());
			perfil.setTipoUsuario(TipoUsuario.COLETOR);
		
			this.perfilRepository.save(perfil);
		} catch (Exception e) {
			throw e;
		}
		
		return createdData;

	}
}