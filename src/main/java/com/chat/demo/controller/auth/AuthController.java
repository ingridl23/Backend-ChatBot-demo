package com.chat.demo.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chat.demo.dto.LoginRequest;
import com.chat.demo.service.auth.JwtService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

	   private final AuthenticationManager authenticationManager;
	    private final UserDetailsService userDetailsService;
	    private final JwtService jwtService;
	    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	    @PostMapping("/login")
	    public String login(@RequestBody LoginRequest request) {
	    	
	    try {	System.out.println("ENTRO LOGIN");
	        System.out.println("username: " + request.getUserName());
	        System.out.println("password: " + request.getPassword());

	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        request.getUserName(),
	                        request.getPassword()
	                )
	        );

	        UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());
	        log.info("Authorities: {}", user.getAuthorities());
	        System.out.println("AUTENTICACION OK");
	        
	    } catch (Exception e) {
	        System.out.println("ERROR LOGIN:");
	        e.printStackTrace();
	        throw e;
	    }

	        UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());

	        return jwtService.generateToken(user);
	    }
}
