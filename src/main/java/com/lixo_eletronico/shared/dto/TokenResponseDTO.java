package com.lixo_eletronico.shared.dto;

public record TokenResponseDTO(String access_token, String refresh_token, String token_type, long expires_in,
		long refresh_expires_in) {
}