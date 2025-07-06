package com.lixo_eletronico.infrastructure.usecases;

import com.lixo_eletronico.enums.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class UsuarioEmpresaData extends UsuarioData {
    private String cnpj;

    public UsuarioEmpresaData () {
        this.setRole(RolesEnum.EMPRESA.toString());
    }

    @Override
    public UserRepresentation build() {
        var user = new UserRepresentation();
        user.setUsername(this.getEmail());
        user.setEmail(this.getEmail());
        user.setFirstName(this.getNome());
        user.setLastName(this.getNome());
        user.setEnabled(true);
        user.setEmailVerified(true);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("cnpj", List.of(this.getCnpj()));

        user.setAttributes(attributes);

        return user;
    }
}
