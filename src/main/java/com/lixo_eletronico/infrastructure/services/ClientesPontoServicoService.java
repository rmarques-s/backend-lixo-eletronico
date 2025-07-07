package com.lixo_eletronico.infrastructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lixo_eletronico.domain.enums.TipoUsuario;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.interfaces.controllers.EmpresaDisponivelDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientesPontoServicoService {

    private final PerfilUsuarioRepository perfilUsuarioRepository;

    public List<EmpresaDisponivelDTO> listarEmpresasDisponiveis() {
        return perfilUsuarioRepository.findAllByTipoUsuario(TipoUsuario.EMPRESA)
                .stream()
                .map(EmpresaDisponivelDTO::new)
                .collect(Collectors.toList());
    }
}