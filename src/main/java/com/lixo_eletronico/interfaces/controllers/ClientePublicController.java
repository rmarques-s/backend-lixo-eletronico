package com.lixo_eletronico.interfaces.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.services.ClienteService;
import com.lixo_eletronico.infrastructure.services.interfaces.IUsuarioService;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteResponseDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioEmpresaRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioEmpresaResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/clientes")
public class ClientePublicController {
	
	private final IUsuarioService<CreateUsuarioClienteResponseDTO, CreateUsuarioClienteRequestDTO> usuarioService;

	public ClientePublicController(@Qualifier("clienteService") IUsuarioService<CreateUsuarioClienteResponseDTO, CreateUsuarioClienteRequestDTO> usuarioService) {
	    this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity<CreateUsuarioClienteResponseDTO> cadastrarCliente(@Valid @RequestBody CreateUsuarioClienteRequestDTO request) {
		var clienteCriado = usuarioService.cadastrarUsuario(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
	}
}
