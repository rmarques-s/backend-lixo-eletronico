package com.lixo_eletronico.interfaces.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.services.ClientesPontoServicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes/pontos-servico")
@RequiredArgsConstructor
public class ClientesPontoServicoController {

    private final ClientesPontoServicoService service;

    @GetMapping
    public ResponseEntity<List<EmpresaDisponivelDTO>> listarEmpresas() {
        return ResponseEntity.ok(service.listarEmpresasDisponiveis());
    }
}
