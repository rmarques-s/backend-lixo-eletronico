package com.lixo_eletronico.interfaces.controllers;

import com.lixo_eletronico.shared.dto.ServicoRequestDTO;
import com.lixo_eletronico.shared.dto.ServicoResponseDTO;
import com.lixo_eletronico.shared.dto.AtualizarStatusServicoDTO;
import com.lixo_eletronico.infrastructure.services.ServicosService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas/servicos")
@RequiredArgsConstructor
public class EmpresasServicoController {

    private final ServicosService servicosService;

    @PostMapping
    public ResponseEntity<ServicoResponseDTO> cadastrarServico(
            @RequestBody ServicoRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        String empresaId = jwt.getSubject();
        var response = servicosService.cadastrarServico(empresaId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponseDTO>> listarServicos(
            @AuthenticationPrincipal Jwt jwt) {

        String empresaId = jwt.getSubject();
        var lista = servicosService.listarServicosDaEmpresa(empresaId);
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatusServico(
            @PathVariable Long id,
            @RequestBody AtualizarStatusServicoDTO dto,
            @AuthenticationPrincipal Jwt jwt) {

        String empresaId = jwt.getSubject();
        servicosService.atualizarStatus(empresaId, id, dto);
        return ResponseEntity.noContent().build();
    }
}
