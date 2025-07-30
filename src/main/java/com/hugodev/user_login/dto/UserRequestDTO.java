package com.hugodev.user_login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	
	@NotBlank(message = "Campo obrigatório!")
	private String name;
	@Email
	@NotBlank(message = "Campo obrigatório!")
	private String email;
	@NotBlank(message = "Campo obrigatório!")
	@Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\p{Punct}]).*$",
    message = "A senha deve conter ao menos 1 letra maiúscula, 1 minúscula, 1 dígito e 1 caractere especial")
	private String password;
}
