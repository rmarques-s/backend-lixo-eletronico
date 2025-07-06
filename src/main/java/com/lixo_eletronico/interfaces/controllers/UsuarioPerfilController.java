package com.lixo_eletronico.interfaces.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.services.ClienteService;
import com.lixo_eletronico.infrastructure.services.PerfilService;
import com.lixo_eletronico.shared.dto.DadosPerfilResponseDTO;
import com.lixo_eletronico.shared.dto.DadosPerfilUpdateDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({"/clientes/perfil", "/empresas/perfil", "/coletores/perfil"})
@RequiredArgsConstructor
public class UsuarioPerfilController {

	private final PerfilService perfilService;
	
	@GetMapping
	public ResponseEntity<DadosPerfilResponseDTO> getDadosPerfil(@AuthenticationPrincipal Jwt jwt) {
		String userKeycloakId = jwt.getSubject();
		
		var perfil = perfilService.buscarPerfilUsuario(userKeycloakId);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(perfil);
	}
	
	@PutMapping
	public ResponseEntity<DadosPerfilResponseDTO> atualizarPerfil(
			@AuthenticationPrincipal Jwt jwt,
			@RequestBody DadosPerfilUpdateDTO dadosAtualizados) {

		String userKeycloakId = jwt.getSubject();
		var perfilAtualizado = perfilService.atualizarPerfil(userKeycloakId, dadosAtualizados);
		return ResponseEntity.ok(perfilAtualizado);
	}
	
	
}
