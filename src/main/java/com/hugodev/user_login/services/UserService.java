package com.hugodev.user_login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugodev.user_login.dto.UserRequestDTO;
import com.hugodev.user_login.dto.UserResponseDTO;
import com.hugodev.user_login.entities.User;
import com.hugodev.user_login.repositories.UserRepository;
import com.hugodev.user_login.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
    private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
    
	@Transactional(readOnly = true)
	public UserResponseDTO findById(Long id) {
		User user = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("id invalido"));
		return new UserResponseDTO(user);
	}
	@Transactional(readOnly = true)
	public Page<UserResponseDTO> findAll(Pageable pageable) {
		Page<User> dto = repo.findAll(pageable);
		return dto.map(x -> new UserResponseDTO(x));
	}
	@Transactional
	public UserResponseDTO insert(UserRequestDTO dto) {
		
		User user = User.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.build();
		User savedUser = repo.save(user);
		return UserResponseDTO.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}
	public UserResponseDTO update(Long id, UserRequestDTO dto) {
		try {
			User user = repo.getReferenceById(id);
			user.setEmail(dto.getEmail());
			user.setName(dto.getName());
			user.setPassword(dto.getPassword());
			repo.save(user);
			return new UserResponseDTO(user);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
		}
	}
	@Transactional
	public void deleteById(Long id) {
		if(!repo.existsById(id)) {
			throw new ResourceNotFoundException("Id inválido!");
		}
			repo.deleteById(id);
		
	}
}
