package com.hugodev.user_login.dto;

import com.hugodev.user_login.entities.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
	
	private Long id;
	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String state;
	private String zipCode;
	private Long userId;
	
	public AddressResponseDTO(Address address) {
		this.id = address.getId();
		this.street = address.getStreet();
		this.number = address.getNumber();
		this.complement = address.getComplement();
		this.neighborhood = address.getNeighborhood();
		this.city = address.getCity();
		this.state = address.getState();
		this.zipCode = address.getZipCode();
		this.userId = address.getUser().getId();
		
	}
}
