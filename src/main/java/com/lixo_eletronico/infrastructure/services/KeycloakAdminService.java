package com.lixo_eletronico.infrastructure.services;

import com.lixo_eletronico.infrastructure.usecases.UsuarioData;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.admin.master-realm}")
    private String masterRealm;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.admin.target-realm}")
    private String targetRealm;

    public void criarUsuario(UsuarioData usuarioData) {
        try (Keycloak keycloak = getKeycloakInstance()) {
            // 1. Criar representação do usuário
            UserRepresentation user = usuarioData.build();
            // 2. Definir credenciais
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(usuarioData.getSenha());
            credential.setTemporary(false);
            user.setCredentials(List.of(credential));

            // 3. Criar usuário
            Response response = keycloak.realm(targetRealm).users().create(user);
            if (response.getStatus() != 201) {
                usuarioData.setMotivoErroCriacao("Erro ao criar usuário. Status: " + response.getStatus());
                throw new ResponseStatusException(HttpStatusCode.valueOf(response.getStatus()), "Erro ao criar usuário no Keycloak: " + response.getStatus());
            }

            String userId = CreatedResponseUtil.getCreatedId(response);

            // 4. Atribuir role
            RoleRepresentation role = keycloak.realm(targetRealm).roles().get(usuarioData.getRole()).toRepresentation();

            keycloak.realm(targetRealm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(List.of(role));
        }
    }

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(masterRealm)
                .clientId(adminClientId)
                .username(adminUsername)
                .password(adminPassword)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
