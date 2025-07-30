package com.hugodev.user_login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {
	
	private static final String LETTERS_ONLY_REGEX = "^[\\p{L} ]+$";
	
	@NotBlank(message = "Campo obrigatório!")
	@Pattern(regexp = LETTERS_ONLY_REGEX, message = "A rua deve conter apenas letras e espaços")
	private String street;
	@NotBlank(message = "Campo obrigatório!")
	private String number;
	@Pattern(regexp = LETTERS_ONLY_REGEX, message = "Campo so pode receber letras e espaços")
	private String complement;
	@NotBlank(message = "Campo obrigatório!")
	@Pattern(regexp = LETTERS_ONLY_REGEX, message = "Campo so pode receber letras e espaços")
	private String neighborhood;
	@NotBlank(message = "Campo obrigatório!")
	@Pattern(regexp = LETTERS_ONLY_REGEX, message = "Campo so pode receber letras e espaços")
	private String city;
	@NotBlank(message = "Campo obrigatório!")
	@Pattern(regexp = LETTERS_ONLY_REGEX, message = "Campo so pode receber letras e espaços")
	private String state;
	@NotBlank(message = "Campo obrigatório!")
	@Size(min = 8, max = 8)
	private String zipCode;
}
