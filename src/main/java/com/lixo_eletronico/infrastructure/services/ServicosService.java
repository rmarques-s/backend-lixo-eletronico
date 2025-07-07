package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.domain.entities.Servico;
import com.lixo_eletronico.domain.repositories.ServicoRepository;
import com.lixo_eletronico.domain.repositories.PerfilUsuarioRepository;
import com.lixo_eletronico.shared.dto.ServicoRequestDTO;
import com.lixo_eletronico.shared.dto.ServicoResponseDTO;
import com.lixo_eletronico.shared.dto.AtualizarStatusServicoDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicosService {

    private final ServicoRepository servicoRepository;
    private final PerfilUsuarioRepository perfilRepository;

    public ServicoResponseDTO cadastrarServico(String empresaKeycloakId, ServicoRequestDTO dto) {
        var perfilOp = perfilRepository.findByIdKeycloak(empresaKeycloakId);
        if (perfilOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não encontrada");
        }

        Servico servico = new Servico();
        servico.setTitulo(dto.getTitulo());
        servico.setDescricao(dto.getDescricao());
        servico.setStatus(true); // serviço criado começa como ativo
        servico.setEmpresa(perfilOp.get());

        return new ServicoResponseDTO(servicoRepository.save(servico));
    }

    public List<ServicoResponseDTO> listarServicosDaEmpresa(String empresaKeycloakId) {
        return servicoRepository.findAllByEmpresa_IdKeycloak(empresaKeycloakId).stream()
                .map(ServicoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void atualizarStatusServico(String empresaKeycloakId, Long id, AtualizarStatusServicoDTO dto) {
        var servico = servicoRepository.findByIdAndEmpresa_IdKeycloak(id, empresaKeycloakId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

        servico.setStatus(dto.getStatus());
        servicoRepository.save(servico);
    }
}
