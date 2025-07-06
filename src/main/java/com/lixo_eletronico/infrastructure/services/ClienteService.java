package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.entities.Endereco;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.enums.RolesEnum;
import com.lixo_eletronico.infrastructure.services.interfaces.IUsuarioService;
import com.lixo_eletronico.infrastructure.usecases.CreateClienteCommandHandler;
import com.lixo_eletronico.infrastructure.usecases.UsuarioClienteData;
import com.lixo_eletronico.infrastructure.usecases.UsuarioData;
import com.lixo_eletronico.infrastructure.usecases.interfaces.ICreateUsuario;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteRequestDTO;
import com.lixo_eletronico.shared.dto.CreateUsuarioClienteResponseDTO;
import com.lixo_eletronico.shared.dto.DadosPerfilResponseDTO;
import com.lixo_eletronico.shared.dto.EnderecoResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("clienteService")
public class ClienteService
		implements IUsuarioService<CreateUsuarioClienteResponseDTO, CreateUsuarioClienteRequestDTO> {

	private final ICreateUsuario handler;
	public final PerfilUsuarioRepository perfilRepository;

	public ClienteService(@Qualifier("createClienteHandler") ICreateUsuario handler,
			PerfilUsuarioRepository perfilRepo) {
		this.handler = handler;
		this.perfilRepository = perfilRepo;
	}

	@Override
	public CreateUsuarioClienteResponseDTO cadastrarUsuario(CreateUsuarioClienteRequestDTO request) {
		UsuarioData usuarioData = new UsuarioClienteData();

		usuarioData.setRole(RolesEnum.CLIENTE.toString());
		usuarioData.setEmail(request.getEmail());
		usuarioData.setSenha(request.getSenha());
		usuarioData.setNome(request.getPrimeiroNome());
		((UsuarioClienteData) usuarioData).setSobrenome(request.getSobrenome());
		usuarioData.setCriado(false);

		try {
			var resultado = handler.execute(usuarioData);

			if (!resultado.isCriado()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Erro ao criar cliente: " + resultado.getMotivoErroCriacao());
			}

			return new CreateUsuarioClienteResponseDTO(request.getPrimeiroNome(), request.getSobrenome(),
					resultado.getEmail(), "Usuário criado com sucesso", resultado.getRole());
		} catch (Exception e) {
			throw e;
		}
	}
}
