package com.hugodev.user_login.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hugodev.user_login.dto.AddressRequestDTO;
import com.hugodev.user_login.dto.AddressResponseDTO;
import com.hugodev.user_login.services.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/users/{userId}/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping
	public ResponseEntity<AddressResponseDTO> insert(@PathVariable Long userId,@Valid @RequestBody AddressRequestDTO dto){
		AddressResponseDTO response = addressService.insertAddress(userId, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}
	@GetMapping(value = "/{addressId}")
	public ResponseEntity<AddressResponseDTO> findById(@PathVariable Long userId, @PathVariable("addressId") Long id){
		AddressResponseDTO response = addressService.findAddressById(id);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<List<AddressResponseDTO>> findAllById(@PathVariable Long userId){
		List<AddressResponseDTO> response = addressService.findAddressByUserId(userId);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping(value = "/{addressId}")
	public ResponseEntity<AddressResponseDTO> update(@PathVariable Long userId,@PathVariable("addressId") Long id,
			@Valid @RequestBody AddressRequestDTO dto){
		AddressResponseDTO response = addressService.addressUpdate(id, dto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{addressId}")
	public ResponseEntity<Void> deleteById(@PathVariable Long userId, @PathVariable Long id){
		addressService.deleteAddressById(id);
		return ResponseEntity.noContent().build();
	}
}
