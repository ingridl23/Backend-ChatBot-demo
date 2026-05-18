package com.chat.demo.service.auth;


import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
//import java.security.Key;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {


	//private static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	 private final SecretKey SECRET;

	    public JwtService(@Value("${JWT_SECRET}") String secret) {
	    	this.SECRET = Keys.hmacShaKeyFor(
	                Decoders.BASE64.decode(secret)
	        );
	    }

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
        		 .subject(userDetails.getUsername()) // ✅ 0.12.x cambio
                 .issuedAt(new Date())
                 .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                 //.signWith(SECRET) // HS256 se infiere o se define explícito si querés
                 .signWith(SECRET, Jwts.SIG.HS256) //explicito con algoritmo le decimos a spring que usar siempre
                 .compact();
    }

    public String extractUsername(String token) {

    	 return Jwts.parser()
                 .verifyWith(SECRET) // ❗ NUEVO en 0.12.x
                 .build()
                 .parseSignedClaims(token)
                 .getPayload()
                 .getSubject();
    }
/*version 0.11.x
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }
    */
    // version 0.12.x
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }
    
   
}
