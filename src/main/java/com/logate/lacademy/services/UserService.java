package com.logate.lacademy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.logate.lacademy.domains.User;
import com.logate.lacademy.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

	/**
	 * Method for getting all users
	 * 
	 * @return List of User Objects
	 */
	public Page<User> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	/**
	 * Method for getting user by identifier
	 * 
	 * @param userId - provided user identifier
	 * @return Optional of User Object
	 */
	public Optional<User> getUserById(Integer userId) {
		return userRepository.findById(userId);
	}
	
	/**
	 * Method for updating user
	 * 
	 * @param user - provided user object
	 * @return updated User Object
	 */
	public User updateUser(User user, Integer id) 
	{
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) 
		{
			User fetchedUser = userOptional.get();
			fetchedUser.setFirstName(user.getFirstName());
			fetchedUser.setLastName(user.getLastName());
			
			return userRepository.save(fetchedUser);
		}
		return null;
	}
	
	/**
	 * Method for storing user object
	 * 
	 * @param user - provided user object
	 * @return 
	 */
	public User store(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * Method for deleting user object
	 * 
	 * @param id - provided user identifier
	 */
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	
	public User updateEntire(User user, Integer id) 
	{
		Optional<User> dbUser = userRepository.findById(id);
		if (dbUser.isPresent()) {
			return userRepository.save(user);
		}
		return null;
	}
	
	public User encodePaswword(User user) {
		passwordEncoder().encode(user.getPassword());
		return user;
	}
}
