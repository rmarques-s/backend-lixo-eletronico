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
public class UsuarioColetorData extends UsuarioData {
	private String cnpj;
	private String cpf;
	private String sobrenome;

	public UsuarioColetorData() {
		this.setRole(RolesEnum.COLETOR.toString());
	}

	@Override
	public UserRepresentation build() {
		var user = new UserRepresentation();
		user.setUsername(this.getEmail());
		user.setEmail(this.getEmail());
		user.setFirstName(this.getNome());
		user.setLastName(this.cnpj != null ? this.getNome() : this.getSobrenome());
		user.setEnabled(true);
		user.setEmailVerified(true);

		Map<String, List<String>> attributes = new HashMap<>();
		if (this.getCnpj() != null) {
			attributes.put("cnpj", List.of(this.getCnpj()));
		} else if (this.getCpf() != null) {
			attributes.put("cpf", List.of(this.getCpf()));
		}
		if (attributes.size() > 0) {
			user.setAttributes(attributes);
		}
		
		return user;
	}
}
