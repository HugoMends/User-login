package com.hugodev.user_login.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugodev.user_login.dto.AddressRequestDTO;
import com.hugodev.user_login.dto.AddressResponseDTO;
import com.hugodev.user_login.entities.Address;
import com.hugodev.user_login.entities.User;
import com.hugodev.user_login.repositories.AddressRepository;
import com.hugodev.user_login.repositories.UserRepository;
import com.hugodev.user_login.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	public AddressResponseDTO insertAddress(Long userId, AddressRequestDTO dto) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario invalido"));
		Address address = new Address();
		address.setStreet(dto.getStreet());
		address.setNumber(dto.getNumber());
		address.setComplement(dto.getComplement());
		address.setNeighborhood(dto.getNeighborhood());
		address.setCity(dto.getCity());
		address.setState(dto.getState());
		address.setZipCode(dto.getZipCode());
		address.setUser(user);
		addressRepo.save(address);
		return new AddressResponseDTO(address);
	}
	
	@Transactional(readOnly = true)
	public AddressResponseDTO findAddressById(Long id) {
		Address address = addressRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
		return new AddressResponseDTO(address);
	}
	@Transactional(readOnly = true)
	public List<AddressResponseDTO> findAddressByUserId(Long id){
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
		return user.getAddress().stream().map(x -> new AddressResponseDTO(x)).collect(Collectors.toList());
	//	List<AddressResponseDTO> result = new ArrayList<>();
	//	for(Address a : user.getAddress()) {
	//		result.add(new AddressResponseDTO(a));
	//	}
	//	return result;
	}
	@Transactional
	public AddressResponseDTO addressUpdate(Long id, AddressRequestDTO dto) {
		try {
			Address address = addressRepo.getReferenceById(id);
			address.setStreet(dto.getStreet());
			address.setNumber(dto.getNumber());
			address.setComplement(dto.getComplement());
			address.setNeighborhood(dto.getNeighborhood());
			address.setCity(dto.getCity());
			address.setState(dto.getState());
			address.setZipCode(dto.getZipCode());
			addressRepo.save(address);
			return new AddressResponseDTO(address);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Endereço não encontrado com ID: " + id);
		}
	}
	@Transactional
	public void deleteAddressById(Long id) {
		if(!addressRepo.existsById(id)) {
			throw new ResourceNotFoundException("Endereço não encontrado");
		}
		addressRepo.deleteById(id);
	}
}
