package com.lixo_eletronico.infrastructure.services.interfaces;

import com.lixo_eletronico.shared.dto.DadosPerfilResponseDTO;

public interface IUsuarioService<T, K> {

	public T cadastrarUsuario(K request);
	
}
