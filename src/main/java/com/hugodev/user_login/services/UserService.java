package com.hugodev.user_login.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hugodev.user_login.dto.UserRequestDTO;
import com.hugodev.user_login.dto.UserResponseDTO;
import com.hugodev.user_login.dto.UserUpdateDTO;
import com.hugodev.user_login.entities.Role;
import com.hugodev.user_login.entities.User;
import com.hugodev.user_login.repositories.RoleRepository;
import com.hugodev.user_login.repositories.UserRepository;
import com.hugodev.user_login.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepo;

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
		// --- 1. Buscando o perfil padrão no banco ---
		// Se o ROLE_USER não for encontrado (o que não deve acontecer se o
		// CommandLineRunner funcionou),
		// uma exceção será lançada.
		Role defaultRole = roleRepo.findByAuthority("ROLE_USER")
				.orElseThrow(() -> new RuntimeException("Perfil ROLE_USER não encontrado."));
		// --- 2. Criando o objeto User ---
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		// --- 3. Atribuindo o perfil padrão ao novo usuário ---
		user.getRoles().add(defaultRole);
		// --- 4. Salvando o usuário ---
		User newUser = repo.save(user);
		return new UserResponseDTO(newUser);
	}

	@Transactional
	public UserResponseDTO update(Long id, UserUpdateDTO dto) {
		// 1. Busca o usuário existente
		User user = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

		// 2. Atualiza o nome e email se os valores no DTO não forem nulos
		if (dto.getName() != null) {
			user.setName(dto.getName());
		}
		if (dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
		}

		// 3. Limpa as roles existentes
		user.getRoles().clear();

		// 4. Busca e adiciona as novas roles
		Set<Role> newRoles = dto.getRoles().stream()
				.map(roleName -> roleRepo.findByAuthority(roleName)
						.orElseThrow(() -> new ResourceNotFoundException("Perfil " + roleName + " não encontrado.")))
				.collect(Collectors.toSet());
		user.getRoles().addAll(newRoles);

		// 5. Salva o usuário
		user = repo.save(user);

		return new UserResponseDTO(user.getId(), user.getName(), user.getEmail());
	}

	@Transactional
	public void deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new ResourceNotFoundException("Id inválido!");
		}
		repo.deleteById(id);

	}
}
