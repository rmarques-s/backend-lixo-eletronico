package com.lixo_eletronico.interfaces.controllers;

import com.lixo_eletronico.infrastructure.services.ColetasService;
import com.lixo_eletronico.shared.dto.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/coletas")
@RequiredArgsConstructor
public class ClientesColetasController {

    private final ColetasService coletasService;

    @PostMapping
    public ResponseEntity<Void> solicitarColeta(
            @RequestBody ColetaRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        coletasService.solicitarColeta(jwt.getSubject(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ColetaResponseDTO>> listarColetas(
            @AuthenticationPrincipal Jwt jwt) {

        var lista = coletasService.listarColetasEmAberto(jwt.getSubject());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{id}/agendar")
    public ResponseEntity<Void> agendar(@PathVariable Long id,
                                        @RequestBody ColetaAgendamentoDTO dto,
                                        @AuthenticationPrincipal Jwt jwt) {
        coletasService.agendarColeta(jwt.getSubject(), id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Void> concluir(@PathVariable Long id,
                                         @AuthenticationPrincipal Jwt jwt) {
        coletasService.concluirColeta(jwt.getSubject(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/avaliar")
    public ResponseEntity<Void> avaliar(@PathVariable Long id,
                                        @RequestBody AvaliacaoColetaDTO dto,
                                        @AuthenticationPrincipal Jwt jwt) {
        coletasService.avaliarColeta(jwt.getSubject(), id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
