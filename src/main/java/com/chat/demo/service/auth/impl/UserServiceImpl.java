package com.chat.demo.service.auth.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.demo.dto.UserRequest;
import com.chat.demo.model.Area;
import com.chat.demo.model.Organization;
import com.chat.demo.model.Role;
import com.chat.demo.model.User;
import com.chat.demo.repository.AreaRepository;
import com.chat.demo.repository.OrganizationRepository;
import com.chat.demo.repository.RoleRepository;
import com.chat.demo.repository.UserRepository;
import com.chat.demo.service.auth.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationRepository organizationRepository;
    private final AreaRepository areaRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserRequest request) {
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        Area area = areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("Area not found"));

        Set<Role> roles = new HashSet<>();
        for (Long roleId : request.getRolesId()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
            roles.add(role);
        }

        User user = User.builder()
                .userName(request.getUserName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(request.getEnabled())
                .organization(organization)
                .area(area)
                .roles(roles)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(Long id, String username, String email, String pass) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(username);
        user.setEmail(email);
        if (pass != null && !pass.isBlank()) {
            user.setPassword(passwordEncoder.encode(pass));
        }
        user.setUpdatedAt(new Date());

        userRepository.save(user);
    }
}
