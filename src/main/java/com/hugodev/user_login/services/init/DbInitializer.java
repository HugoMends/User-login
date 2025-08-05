package com.hugodev.user_login.services.init;

import com.hugodev.user_login.entities.Role;
import com.hugodev.user_login.entities.User;
import com.hugodev.user_login.repositories.RoleRepository;
import com.hugodev.user_login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Service
public class DbInitializer {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

  
    @Transactional
    public void initializeDatabase() {
        Role roleAdmin = roleRepository.findByAuthority("ROLE_ADMIN")
            .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

        Role roleUser = roleRepository.findByAuthority("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin_password"));
            adminUser.getRoles().add(roleAdmin);
            userRepository.save(adminUser);
        }
    }
}