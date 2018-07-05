package com.logate.lacademy.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class TokenProvider {
	
	@Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long tokenValidityInSeconds;

    
    /**
     * Method for generating JWT token
     * 
     * @param authentication - security object
     * @return generated token string
     */
    public String generateToken(Authentication authentication)
    {
	    	String authorities = authentication.getAuthorities()
	    			.stream()
	    			.map(authority -> authority.getAuthority())
	    			.collect(Collectors.joining(","));
	    	
	    	long now = new Date().getTime();
	    	Date tokenValidity = new Date(now + tokenValidityInSeconds * 1000);
	    	
	    	return Jwts.builder()
	    			.setSubject(authentication.getName())
	    			.claim("authorities", authorities)
	            .signWith(SignatureAlgorithm.HS512, secretKey)
	            .setExpiration(tokenValidity)
	            .compact();
    }
    
    /**
     * Method for getting authentication object from provided token
     * 
     * @param token - provided token
     * @return Authentication object
     */
    public Authentication getAuthentication(String token)
    {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        String principal = claims.getSubject();
        Collection<? extends GrantedAuthority> authorities =
            Arrays.asList(claims.get("authorities").toString().split(","))
            	.stream()
            .map(authority -> new SimpleGrantedAuthority(authority))
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    
    /**
     * Method for validating provided token
     * 
     * @param token - provided token
     * @return boolean value (valid | not valid)
     */
    public boolean validateToken(String token)
    {
        try
        {
            Jwts.parser()
            	.setSigningKey(secretKey)
            	.parseClaimsJws(token);
            
            return true;
        }
        catch (SignatureException e) {
            return false;
        }
    }
}
