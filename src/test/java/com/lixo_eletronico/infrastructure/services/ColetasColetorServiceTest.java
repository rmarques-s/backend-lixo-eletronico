package com.lixo_eletronico.infrastructure.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.lixo_eletronico.domain.entities.Coleta;
import com.lixo_eletronico.domain.entities.PerfilUsuario;
import com.lixo_eletronico.domain.enums.StatusColeta;
import com.lixo_eletronico.domain.repositories.ColetaRepository;
import com.lixo_eletronico.shared.dto.ColetaDetalheDTO;
import com.lixo_eletronico.shared.dto.ColetaStatusUpdateDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class ColetasColetorServiceTest {

	@Mock
	private ColetaRepository coletaRepository;

	@InjectMocks
	private ColetasColetorService service;

	@Test
	void listarPorColetor_deveRetornarListaDeColetas() {
		String keycloakId = "coletor123";

		// Cria cliente
		PerfilUsuario cliente = new PerfilUsuario();
		cliente.setNome("João");

		// Cria coletor
		PerfilUsuario coletor = new PerfilUsuario();
		coletor.setIdKeycloak(keycloakId);

		// Cria coleta com cliente e coletor setados
		Coleta coleta = new Coleta();
		coleta.setId(1L);
		coleta.setCliente(cliente);
		coleta.setColetor(coletor);

		when(coletaRepository.findAllByColetor_IdKeycloak(keycloakId)).thenReturn(List.of(coleta));

		List<ColetaDetalheDTO> resultado = service.listarPorColetor(keycloakId);

		assertNotNull(resultado);
		assertEquals(1, resultado.size());
	}

	@Test
	void atualizarStatus_deveAtualizarQuandoColetorForProprietario() {
		String keycloakId = "coletor123";
		Long coletaId = 1L;

		PerfilUsuario coletor = new PerfilUsuario();
		coletor.setIdKeycloak(keycloakId);

		Coleta coleta = new Coleta();
		coleta.setId(coletaId);
		coleta.setColetor(coletor);

		ColetaStatusUpdateDTO dto = new ColetaStatusUpdateDTO();
		dto.setStatus(StatusColeta.REALIZADA);

		when(coletaRepository.findById(coletaId)).thenReturn(Optional.of(coleta));

		assertDoesNotThrow(() -> service.atualizarStatus(keycloakId, coletaId, dto));

		verify(coletaRepository).save(coleta);
		assertEquals(StatusColeta.REALIZADA, coleta.getStatus());
	}

	@Test
	void atualizarStatus_deveLancarNotFoundQuandoColetaNaoExiste() {
		when(coletaRepository.findById(anyLong())).thenReturn(Optional.empty());

		ColetaStatusUpdateDTO dto = new ColetaStatusUpdateDTO();
		dto.setStatus(StatusColeta.REALIZADA);

		ResponseStatusException ex = assertThrows(ResponseStatusException.class,
				() -> service.atualizarStatus("qualquer", 99L, dto));

		assertEquals(404, ex.getStatusCode().value());
	}

	@Test
	void atualizarStatus_deveLancarForbiddenQuandoColetorNaoForDono() {
		String keycloakId = "coletor_certo";
		String outroKeycloakId = "coletor_errado";

		PerfilUsuario coletor = new PerfilUsuario();
		coletor.setIdKeycloak(outroKeycloakId); // coletor errado

		Coleta coleta = new Coleta();
		coleta.setId(1L);
		coleta.setColetor(coletor);

		when(coletaRepository.findById(1L)).thenReturn(Optional.of(coleta));

		ColetaStatusUpdateDTO dto = new ColetaStatusUpdateDTO();
		dto.setStatus(StatusColeta.REALIZADA);

		ResponseStatusException ex = assertThrows(ResponseStatusException.class,
				() -> service.atualizarStatus(keycloakId, 1L, dto));

		assertEquals(403, ex.getStatusCode().value());
	}
}
