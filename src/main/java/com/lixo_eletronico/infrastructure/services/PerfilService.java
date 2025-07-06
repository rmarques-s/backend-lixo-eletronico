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
import com.lixo_eletronico.shared.dto.DadosPerfilUpdateDTO;
import com.lixo_eletronico.shared.dto.EnderecoResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PerfilService {

	public final PerfilUsuarioRepository perfilRepository;

	public DadosPerfilResponseDTO buscarPerfilUsuario(String keycloakUserId) {
		var perfilUsuarioOp = this.perfilRepository.findByIdKeycloak(keycloakUserId);

		if (perfilUsuarioOp.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar cliente usuário não encontrado");
		}

		var perfilUsuario = perfilUsuarioOp.get();

		var dadosPerfilResponse = new DadosPerfilResponseDTO(perfilUsuario);

		return dadosPerfilResponse;
	}

	public DadosPerfilResponseDTO atualizarPerfil(String keycloakUserId, DadosPerfilUpdateDTO dto) {
	    var perfilUsuarioOp = this.perfilRepository.findByIdKeycloak(keycloakUserId);

	    if (perfilUsuarioOp.isEmpty()) {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar perfil: usuário não encontrado");
	    }

	    var perfil = perfilUsuarioOp.get();

	    if (dto.getNome() != null) perfil.setNome(dto.getNome());
	    if (dto.getSobrenome() != null) perfil.setSobrenome(dto.getSobrenome());
	    if (dto.getEmail() != null) perfil.setEmail(dto.getEmail());
	    if (dto.getCpf() != null) perfil.setCpf(dto.getCpf());
	    if (dto.getCnpj() != null) perfil.setCnpj(dto.getCnpj());
	    if (dto.getCelular() != null) perfil.setCelular(dto.getCelular());
	    if (dto.getDataNascimento() != null) perfil.setDataNascimento(dto.getDataNascimento());

	    if (dto.getEndereco() != null) {
	        var enderecoDTO = dto.getEndereco();
	        var endereco = perfil.getEndereco();

	        if (endereco == null) {
	            endereco = new Endereco();
	        }

	        if (enderecoDTO.getCep() != null) endereco.setCep(enderecoDTO.getCep());
	        if (enderecoDTO.getRua() != null) endereco.setRua(enderecoDTO.getRua());
	        if (enderecoDTO.getNumero() != null) endereco.setNumero(enderecoDTO.getNumero());
	        if (enderecoDTO.getComplemento() != null) endereco.setComplemento(enderecoDTO.getComplemento());
	        if (enderecoDTO.getBairro() != null) endereco.setBairro(enderecoDTO.getBairro());
	        if (enderecoDTO.getCidade() != null) endereco.setCidade(enderecoDTO.getCidade());
	        if (enderecoDTO.getEstado() != null) endereco.setEstado(enderecoDTO.getEstado());

	        perfil.setEndereco(endereco);
	    }

	    perfilRepository.save(perfil);

	    return new DadosPerfilResponseDTO(perfil);
	}

}
