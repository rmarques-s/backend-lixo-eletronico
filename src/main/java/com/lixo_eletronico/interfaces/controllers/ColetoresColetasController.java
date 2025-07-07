package com.lixo_eletronico.interfaces.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.services.ColetasColetorService;
import com.lixo_eletronico.shared.dto.ColetaDetalheDTO;
import com.lixo_eletronico.shared.dto.ColetaStatusUpdateDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/coletores/coletas")
@RequiredArgsConstructor
public class ColetoresColetasController {

    private final ColetasColetorService service;

    @GetMapping
    public ResponseEntity<List<ColetaDetalheDTO>> listar(
            @AuthenticationPrincipal Jwt jwt) {
        var lista = service.listarPorColetor(jwt.getSubject());
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id,
                                                @RequestBody ColetaStatusUpdateDTO dto,
                                                @AuthenticationPrincipal Jwt jwt) {
        service.atualizarStatus(jwt.getSubject(), id, dto);
        return ResponseEntity.noContent().build();
    }
}
