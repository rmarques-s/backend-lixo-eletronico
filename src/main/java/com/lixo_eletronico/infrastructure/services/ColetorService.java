package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.enums.RolesEnum;
import com.lixo_eletronico.infrastructure.services.interfaces.IUsuarioService;
import com.lixo_eletronico.infrastructure.usecases.CreateClienteCommandHandler;
import com.lixo_eletronico.infrastructure.usecases.UsuarioColetorData;
import com.lixo_eletronico.infrastructure.usecases.UsuarioData;
import com.lixo_eletronico.infrastructure.usecases.UsuarioEmpresaData;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteResponseDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioColetorRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioColetorResponseDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioEmpresaRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioEmpresaResponseDTO;
import com.lixo_eletronico.shared.dto.DadosPerfilResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("coletorService")
public class ColetorService
		implements IUsuarioService<CreateUsuarioColetorResponseDTO, CreateUsuarioColetorRequestDTO> {

	private final ICreateUsuario handler;

	public ColetorService(@Qualifier("createColetorHandler") ICreateUsuario handler) {
		this.handler = handler;
	}

	public CreateUsuarioColetorResponseDTO cadastrarUsuario(CreateUsuarioColetorRequestDTO request) {
		UsuarioData usuarioData = new UsuarioColetorData();

		usuarioData.setEmail(request.getEmail());
		usuarioData.setSenha(request.getSenha());
		usuarioData.setNome(request.getNome());
		((UsuarioColetorData) usuarioData).setSobrenome(request.getSobrenome());
		((UsuarioColetorData) usuarioData).setCnpj(request.getCnpj());
		((UsuarioColetorData) usuarioData).setCpf(request.getCpf());
		usuarioData.setCriado(false);

		try {
			var resultado = handler.execute(usuarioData);

			if (!resultado.isCriado()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Erro ao criar cliente: " + resultado.getMotivoErroCriacao());
			}

			return new CreateUsuarioColetorResponseDTO(request.getNome(), request.getSobrenome(), request.getEmail(),
					"Usuário criado com sucesso", resultado.getRole());
		} catch (Exception e) {
			throw e;
		}
	}
}
