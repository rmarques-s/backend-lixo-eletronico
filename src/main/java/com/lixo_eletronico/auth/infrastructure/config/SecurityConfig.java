package com.lixo_eletronico.auth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.lixo_eletronico.auth.infrastructure.config.converter.KeycloakJwtConverter;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/public/**").permitAll()
						.requestMatchers("/api/v1/clientes/**").hasRole("CLIENTE")
						.requestMatchers("/api/v1/coletores/**").hasRole("COLETOR")
						.requestMatchers("/api/v1/empresas/**").hasRole("EMPRESA").requestMatchers("/api/v1/admin/**")
						.hasRole("ADMIN").anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtConverter())));

		return http.build();
	}
}
