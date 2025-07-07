package com.lixo_eletronico.interfaces.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.services.ClientesPontoColetaService;
import com.lixo_eletronico.shared.dto.ColetorDisponivelDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes/pontos-coleta")
@RequiredArgsConstructor
public class ClientesPontoColetaController {

    private final ClientesPontoColetaService service;

    @GetMapping
    public ResponseEntity<List<ColetorDisponivelDTO>> listarColetores() {
        return ResponseEntity.ok(service.listarColetoresDisponiveis());
    }
}