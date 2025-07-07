package com.lixo_eletronico.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lixo_eletronico.domain.enums.TipoUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.shared.dto.ColetorDisponivelDTO;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class ClientesPontoColetaService {

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public List<ColetorDisponivelDTO> listarColetoresDisponiveis() {
        return perfilUsuarioRepository.findAllByTipoUsuario(TipoUsuario.COLETOR)
                .stream()
                .map(ColetorDisponivelDTO::new)
                .collect(Collectors.toList());
    }
}