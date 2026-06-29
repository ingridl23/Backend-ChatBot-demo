package com.chat.demo;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.chat.demo.model.Role;
import com.chat.demo.model.User;
import com.chat.demo.model.Area;
import com.chat.demo.model.DocumentStatus;
import com.chat.demo.model.Organization;
import com.chat.demo.repository.AreaRepository;
import com.chat.demo.repository.DocumentStatusRepository;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.repository.RoleRepository;
import com.chat.demo.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Seed de datos para desarrollo y pruebas. Solo activo con el perfil "dev".
 * No debe ejecutarse en producción.
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository  userRepository;
    private final RoleRepository  roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AreaRepository  areaRepo;
    private final OrganizationRepository  organizationRep;
    private final DocumentStatusRepository statusRepository;

    @PostConstruct
    public void init() {
        log.info("=== DATA INITIALIZER EJECUTANDO (perfil dev) ===");

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));

        Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("USER").build()));

        if (organizationRep.findById(1L).isEmpty()) {
            Organization org1 = Organization.builder()
                    .name("Empresa Uno")
                    .primaryColor("blue")
                    .secondaryColor("white")
                    .domain("empresa.gov.ar")
                    .supportEmail("atencionalcliente@soporte.com.ar")
                    .build();
            organizationRep.save(org1);
            log.info("Organización creada: Empresa Uno");
        }

        if (areaRepo.findById(1L).isEmpty()) {
            Organization org1 = organizationRep.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Organización no encontrada"));
            Area area1 = Area.builder()
                    .name("Sistemas")
                    .description("Centro de cómputos empresa uno")
                    .organization(org1)
                    .build();
            areaRepo.save(area1);
            log.info("Área creada: Sistemas");
        }

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
            log.info("Usuario admin creado");
        }

        if (userRepository.findByUserName("user").isEmpty()) {
            Role foundUserRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER no existe"));
            User user = User.builder()
                    .userName("user")
                    .lastName("gonzalez")
                    .email("user@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .enabled(true)
                    .roles(Set.of(foundUserRole))
                    .build();
            userRepository.save(user);
            log.info("Usuario user creado");
        }

        if (statusRepository.findById(1L).isEmpty()) {
            DocumentStatus status = DocumentStatus.builder().name("ACTIVO").build();
            statusRepository.save(status);
            log.info("DocumentStatus ACTIVO creado");
        }
    }
}
