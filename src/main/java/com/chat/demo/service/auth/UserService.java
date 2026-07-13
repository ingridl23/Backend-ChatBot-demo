package com.chat.demo.service.auth;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.chat.demo.dto.ChangePasswordRequest;
import com.chat.demo.dto.UpdateProfileRequest;
import com.chat.demo.dto.UserRequest;
import com.chat.demo.model.User;



public interface UserService {
	 User createUser(UserRequest request);

	    List<User> getAllUsers();

	    List<User> getUsersByOrganization(Long organizationId);

	    List<User> getUsersByArea(Long areaId);

	    Optional<User> getUserById(Long id);

	    void deleteUser(Long id);
	    void updateUser(Long id, String username, String email, String pass, Long areaId, Set<Long> rolesId);

	    User updateProfile(Long id, UpdateProfileRequest request);
	    void changePassword(Long id, ChangePasswordRequest request);
}
