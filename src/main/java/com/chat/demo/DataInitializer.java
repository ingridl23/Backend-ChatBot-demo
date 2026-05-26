package com.chat.demo;

import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.chat.demo.model.Role;
import com.chat.demo.model.User;
import com.chat.demo.repository.RoleRepository;
import com.chat.demo.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * clase para realizar pruebas de endpoint con un usuario de prueba y chequear que la funcionalidad y la seguridad implementada
 * funcione correctamente siguiendo el orden y el objetivo de negocio o proyecto asociado.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        if (userRepository.findByUsername("admin").isEmpty()) {

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN no existe"));

            User admin = User.builder()
                    .userName("admin")
                    .lastName("perez")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .enabled(true)
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);

            System.out.println("ADMIN CREADO: admin / 1234");
        }
        
        
        if (userRepository.findByUsername("user").isEmpty()) {
        	  Role userRole = roleRepository.findByName("USER") .orElseThrow(() -> new RuntimeException("Role USER aun no existe"));

              User user = User.builder()
                      .userName("user")
                      .lastName("gonzalez")
                      .email("user@gmail.com")
                      .password(passwordEncoder.encode("12345"))
                      .enabled(true)
                      .roles(Set.of(userRole))
                     
                      .build();

              userRepository.save(user);
              System.out.println("USER CREADO: user / 12345");
        }
    }
	
	
	
}

