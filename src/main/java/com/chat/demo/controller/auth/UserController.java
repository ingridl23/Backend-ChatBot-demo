package com.chat.demo.controller.auth;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.AdminUpdateUserRequest;
import com.chat.demo.dto.ChangePasswordRequest;
import com.chat.demo.dto.UpdateProfileRequest;
import com.chat.demo.dto.UserRequest;
import com.chat.demo.dto.UserResponse;
import com.chat.demo.mapper.UserMapper;
import com.chat.demo.model.Area;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.auth.UserService;
import com.chat.demo.service.rag.AreaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final AreaService areaService;

    private User currentUser(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOrganization() == null) {
            throw new RuntimeException("Authenticated user has no organization assigned");
        }
        return user;
    }

    private boolean hasRole(User user, String roleName) {
        return user.getRoles().stream().anyMatch(r -> roleName.equals(r.getName()));
    }

    private User findOwnedOrThrow(Long id, Long callerOrganizationId) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getOrganization() == null || !user.getOrganization().getId().equals(callerOrganizationId)) {
            throw new AccessDeniedException("User does not belong to your organization");
        }
        return user;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(@Valid @RequestBody UserRequest request, Authentication authentication) {
        User caller = currentUser(authentication);
        // El organizationId nunca se toma del cliente: el nuevo usuario siempre
        // queda en la misma organización que el admin que lo crea.
        request.setOrganizationId(caller.getOrganization().getId());
        return userMapper.toResponse(userService.createUser(request));
    }

    // Listar los integrantes de la propia organización.
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getOrganizationUsers(Authentication authentication) {
        User caller = currentUser(authentication);
        return userService.getUsersByOrganization(caller.getOrganization().getId())
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // Listar los integrantes de un área puntual. Un ADMIN puede ver cualquier área de su
    // organización; un AREA_ADMIN solo la propia.
    @GetMapping("/area/{areaId}")
    @PreAuthorize("hasAnyRole('ADMIN','AREA_ADMIN')")
    public List<UserResponse> getAreaUsers(@PathVariable Long areaId, Authentication authentication) {
        User caller = currentUser(authentication);
        Area area = areaService.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Area not found"));

        if (!area.getOrganization().getId().equals(caller.getOrganization().getId())) {
            throw new AccessDeniedException("Area does not belong to the authenticated user's organization");
        }
        if (!hasRole(caller, "ADMIN")) {
            if (caller.getArea() == null || !caller.getArea().getId().equals(areaId)) {
                throw new AccessDeniedException("AREA_ADMIN can only view members of their own area");
            }
        }

        return userService.getUsersByArea(areaId)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // Un ADMIN edita a cualquier usuario de su propia organización.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody AdminUpdateUserRequest request,
            Authentication authentication) {
        User caller = currentUser(authentication);
        User target = findOwnedOrThrow(id, caller.getOrganization().getId());
        userService.updateUser(target.getId(), request.getUserName(), request.getEmail(), request.getPassword(),
                request.getAreaId(), request.getRolesId());
        return userMapper.toResponse(userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    // Un ADMIN borra a cualquier usuario de su propia organización.
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, Authentication authentication) {
        User caller = currentUser(authentication);
        findOwnedOrThrow(id, caller.getOrganization().getId());
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return userMapper.toResponse(user);
    }

    @PutMapping("/me")
    public UserResponse updateProfile(Authentication authentication,
                                       @Valid @RequestBody UpdateProfileRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User updated = userService.updateProfile(userDetails.getUser().getId(), request);
        return userMapper.toResponse(updated);
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(Authentication authentication,
                                                @Valid @RequestBody ChangePasswordRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userService.changePassword(userDetails.getUser().getId(), request);
        return ResponseEntity.noContent().build();
    }
}
