package com.lixo_eletronico.infrastructure.usecases.interfaces;

import com.lixo_eletronico.infrastructure.usecases.UsuarioData;

public interface ICreateUsuario {
	public UsuarioData execute(UsuarioData data) throws RuntimeException;
}
