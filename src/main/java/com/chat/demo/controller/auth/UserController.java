package com.chat.demo.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.demo.dto.ChangePasswordRequest;
import com.chat.demo.dto.UpdateProfileRequest;
import com.chat.demo.dto.UserResponse;
import com.chat.demo.mapper.UserMapper;
import com.chat.demo.model.User;
import com.chat.demo.service.auth.CustomUserDetails;
import com.chat.demo.service.auth.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

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
