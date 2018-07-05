package com.logate.lacademy.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logate.lacademy.security.jwt.JWTToken;
import com.logate.lacademy.security.jwt.TokenProvider;
import com.logate.lacademy.web.dto.LoginDTO;

@Description(value = "Authentication Controller.")
@RestController
public class AuthController {
	
	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
    private AuthenticationManager authenticationManager;
	
	
	@RequestMapping(value = "/auth/login", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> authorize(@Valid @RequestBody LoginDTO login)
    {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        try
        {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            String jwt = tokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new JWTToken(jwt), HttpStatus.OK);
        }
        catch (AuthenticationException exception) {
            return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
