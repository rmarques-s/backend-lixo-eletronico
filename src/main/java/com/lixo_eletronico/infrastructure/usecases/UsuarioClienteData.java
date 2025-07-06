package com.lixo_eletronico.infrastructure.usecases;

import com.lixo_eletronico.enums.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;

@Data
@AllArgsConstructor
public class UsuarioClienteData extends UsuarioData {
    private String sobrenome;

    public UsuarioClienteData () {
        this.setRole(RolesEnum.CLIENTE.toString());
    }

    @Override
    public UserRepresentation build() {
        var user = new UserRepresentation();
        user.setUsername(this.getEmail());
        user.setEmail(this.getEmail());
        user.setFirstName(this.getNome());
        user.setLastName(this.getSobrenome());
        user.setEnabled(true);
        user.setEmailVerified(true);

        return user;
    }
}
