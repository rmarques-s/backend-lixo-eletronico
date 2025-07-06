package com.lixo_eletronico.interfaces.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lixo_eletronico.infrastructure.usecases.LoginCommandHandler;
import com.lixo_eletronico.shared.dto.LoginRequestDTO;
import com.lixo_eletronico.shared.dto.TokenResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginCommandHandler loginCommandHandler;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
    	TokenResponseDTO token = loginCommandHandler.execute(request.getEmail(), request.getSenha());
        
        return ResponseEntity.ok(token);
    }
}