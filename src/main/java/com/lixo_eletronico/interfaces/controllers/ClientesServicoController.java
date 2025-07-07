package com.lixo_eletronico.interfaces.controllers;

import com.lixo_eletronico.infrastructure.services.ClienteServicosService;
import com.lixo_eletronico.shared.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/servicos")
@RequiredArgsConstructor
public class ClientesServicoController {

    private final ClienteServicosService servicosClienteService;

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarServicosDisponiveis() {
        var lista = servicosClienteService.listarServicosDisponiveis();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{id}/chat")
    public ResponseEntity<Void> iniciarChat(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        servicosClienteService.iniciarChat(id, jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/agendar")
    public ResponseEntity<Void> agendarServico(
            @PathVariable Long id,
            @RequestBody AgendamentoRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        servicosClienteService.agendarServico(id, jwt.getSubject(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        servicosClienteService.cancelarAgendamento(id, jwt.getSubject());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/avaliar")
    public ResponseEntity<Void> avaliar(@PathVariable Long id, @RequestBody AvaliacaoServicoDTO dto,
                                        @AuthenticationPrincipal Jwt jwt) {
        servicosClienteService.avaliarServico(id, jwt.getSubject(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}