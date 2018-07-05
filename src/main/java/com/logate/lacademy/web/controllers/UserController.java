package com.logate.lacademy.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logate.lacademy.domains.Role;
import com.logate.lacademy.domains.User;
import com.logate.lacademy.filters.Filter;
import com.logate.lacademy.repository.RoleRepository;
import com.logate.lacademy.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	@RequestMapping(
			value = "/users", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers(Pageable pageable)
	{
		Page<User> users = userService.getAllUsers(pageable);
		return new ResponseEntity<>(users.getContent(), HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/users/{id}", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Integer userId)
	{
		Optional<User> user = userService.getUserById(userId);
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/users/{id}", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer id,
										   @RequestBody User user)
	{
		if (user.getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User updatedUser = userService.updateUser(user, id);
		return Optional.ofNullable(updatedUser)
				.map(usr -> new ResponseEntity<>(usr, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@RequestMapping(value = "/users",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> storeUser(@RequestBody User user)
	{
		if (user.getId() != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User storedUser = userService.store(user);
		return Optional.ofNullable(storedUser)
				.map(usr -> new ResponseEntity<>(usr, HttpStatus.CREATED))
				.orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}
	
	@RequestMapping(value = "/users/{id}", 
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Integer id)
	{
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users-params", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> mapQueryParams(
			@RequestParam(value = "firstName") String firstName, 
			@RequestParam(value = "lastName") String lastName)
	{
		LOGGER.info("Request param 1: {} *** Request Param 2: {}", firstName, lastName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/users-map-params", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> mapQueryParams(
			@RequestParam Map<String, Object> params)
	{
		LOGGER.info("Request params: {}", params);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users-model-attrs", 
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> mapModelAttributes(@ModelAttribute Filter filter)
	{
		LOGGER.info("Request params: {}", filter);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/entire/{id}", 
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateEntireObject(@PathVariable(value = "id") Integer id, @RequestBody User user)
	{
		user = userService.updateEntire(user, id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createNew(@Valid @RequestBody User user)
	{
		//dodavanje role user za novog usera
		Role roleUser = roleRepository.findRoleByName();
		Set<Role> roles = new HashSet<>();
		roles.add(roleUser);
		user.setRoles(roles);
		userService.encodePaswword(user);
		User dbUser = userService.store(user);
		return new ResponseEntity<>(dbUser, HttpStatus.CREATED);
	}
}
