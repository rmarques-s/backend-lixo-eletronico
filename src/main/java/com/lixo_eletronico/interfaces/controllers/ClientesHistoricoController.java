package com.lixo_eletronico.interfaces.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.services.ClientesHistoricoService;
import com.lixo_eletronico.shared.dto.ColetaHistoricoDTO;
import com.lixo_eletronico.shared.dto.ServicoHistoricoDTO;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/clientes/historico")
@RequiredArgsConstructor
public class ClientesHistoricoController {

 private final ClientesHistoricoService historicoService;

 @GetMapping("/coletas")
 public ResponseEntity<List<ColetaHistoricoDTO>> listarColetas(@AuthenticationPrincipal Jwt jwt) {
     String keycloakId = jwt.getSubject();
     return ResponseEntity.ok(historicoService.listarColetasFinalizadas(keycloakId));
 }

 @GetMapping("/servicos")
 public ResponseEntity<List<ServicoHistoricoDTO>> listarServicos(@AuthenticationPrincipal Jwt jwt) {
     String keycloakId = jwt.getSubject();
     return ResponseEntity.ok(historicoService.listarServicosUtilizados(keycloakId));
 }
}
