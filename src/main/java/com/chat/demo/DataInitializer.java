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
    	
    	Role adminRole = roleRepository.findByName("ADMIN")
    	        .orElseGet(() ->
    	                roleRepository.save(
    	                        Role.builder()
    	                                .name("ADMIN")
    	                                .build()
    	                ));

    	Role userRole = roleRepository.findByName("USER")
    	        .orElseGet(() ->
    	                roleRepository.save(
    	                        Role.builder()
    	                                .name("USER")
    	                                .build()
    	                ));

        if (userRepository.findByUserName("admin").isEmpty()) {

            Role foundAdminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN no existe"));

            User admin = User.builder()
                    .userName("admin")
                    .lastName("perez")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .enabled(true)
                    .roles(Set.of(foundAdminRole))
                    .build();

            userRepository.save(admin);

            System.out.println("ADMIN CREADO: admin / 1234");
        }
        
        
        if (userRepository.findByUserName("user").isEmpty()) {
        	  Role foundUserRole = roleRepository.findByName("USER") .orElseThrow(() -> new RuntimeException("Role USER aun no existe"));

              User user = User.builder()
                      .userName("user")
                      .lastName("gonzalez")
                      .email("user@gmail.com")
                      .password(passwordEncoder.encode("12345"))
                      .enabled(true)
                      .roles(Set.of(foundUserRole))
                     
                      .build();

              userRepository.save(user);
              System.out.println("USER CREADO: user / 12345");
        }
    }
	
	
	
}

