package com.chat.demo.service.auth;

import java.util.List;
import java.util.Optional;

import com.chat.demo.dto.ChangePasswordRequest;
import com.chat.demo.dto.UpdateProfileRequest;
import com.chat.demo.dto.UserRequest;
import com.chat.demo.model.User;



public interface UserService {
	 User createUser(UserRequest request);

	    List<User> getAllUsers();

	    Optional<User> getUserById(Long id);

	    void deleteUser(Long id);
	    void updateUser(Long id,String username,String email,String pass);

	    User updateProfile(Long id, UpdateProfileRequest request);
	    void changePassword(Long id, ChangePasswordRequest request);
}
