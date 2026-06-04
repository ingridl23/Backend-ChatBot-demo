package com.chat.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

	   

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	        return http
	                .csrf(csrf -> csrf.disable())
	                .sessionManagement(session ->
	                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                )
	                .authorizeHttpRequests(auth -> auth

	                        .requestMatchers("/api/auth/**").permitAll()

	                        .requestMatchers("/api/users/**").hasRole("ADMIN")
	                        .requestMatchers("/api/roles/**").hasRole("ADMIN")
	                        .requestMatchers("/api/permissions/**").hasRole("ADMIN")

	                        .requestMatchers("/api/documents/**").hasAnyRole("ADMIN","USER")
	                   
                            .requestMatchers("/api/chat/**").hasAnyRole("ADMIN","USER")
	                        .anyRequest().authenticated()
	                )
	                .authenticationProvider(authenticationProvider())
	                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	                .build();
	    }
	    
	    
	    //creamos authentication manager
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
	    @Bean
	    public AuthenticationProvider authenticationProvider() {

	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(userDetailsService);
	        provider.setPasswordEncoder(passwordEncoder());

	        return provider;
	    }
	    //password encoder
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	         return new BCryptPasswordEncoder();
	    }
	
	
}
