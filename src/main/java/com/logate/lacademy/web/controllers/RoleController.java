package com.logate.lacademy.web.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logate.lacademy.domains.Role;
import com.logate.lacademy.services.RoleService;

@RestController
@RequestMapping(value = "/api")
public class RoleController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/roles", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> createNew(@Valid @RequestBody Role role)
	{
		Role dbRole = roleService.store(role);
		return new ResponseEntity<>(dbRole, HttpStatus.CREATED);
	}
}
