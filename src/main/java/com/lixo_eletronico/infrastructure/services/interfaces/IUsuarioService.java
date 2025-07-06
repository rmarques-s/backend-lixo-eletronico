package com.lixo_eletronico.infrastructure.services.interfaces;


public interface IUsuarioService<T, K> {

	public T cadastrarUsuario(K request);
	
}
