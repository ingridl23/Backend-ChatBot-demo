package com.chat.demo.service.auth.impl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.chat.demo.model.User;
import com.chat.demo.repository.UserRepository;
import com.chat.demo.service.auth.CustomUserDetails;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
	public class UserDetailsServiceImpl implements UserDetailsService {

		    private final UserRepository userRepository;

		    @Override
		    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		    	 User user = userRepository.findByUserName(username)
		    	            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		    	    return new CustomUserDetails(user);
		    }
		
	}


