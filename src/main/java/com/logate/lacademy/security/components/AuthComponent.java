package com.logate.lacademy.security.components;

//import java.awt.List;
//import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.core.env.Environment;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.logate.lacademy.config.MicroserviceConfiguration;
//import com.logate.lacademy.domains.Comments;
//import com.logate.lacademy.domains.User;
//import com.logate.lacademy.repository.CommentsRepository;
//import com.logate.lacademy.repository.UserRepository;

@Description(value = "Authentication Component.")
@Component(value = "authComponent")
public class AuthComponent {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthComponent.class);
	
	
	
	@Value("${microservice.key}")
	private String key;
	
	@Autowired
	private Environment environment; 
	
	@Autowired
	private MicroserviceConfiguration microConf;
	
//	@Autowired
//	private CommentsRepository commentsRepository;
//	
//	@Autowired
//	private UserRepository userRepository;
	
	/**
	 * Method for checking permission
	 * 
	 * @return boolean value (has permission or not)
	 */
	public boolean hasPermission() 
	{
		return false;
	}
	
	/**
	 * Method for checking Authorization header value
	 * 
	 * @param authHeaderValue
	 * @return
	 */
	public boolean hasHeaderPermission(String authHeaderValue) 
	{
		LOGGER.info("Header value: {}", authHeaderValue);
		if (authHeaderValue.trim().contentEquals(key)) {
			return true;
		}
		return false;
	}
	
//	public boolean checkComments(Integer articleId) {
//		SecurityContext secCon = SecurityContextHolder.getContext();
//		Authentication auth = secCon.getAuthentication();
//		String username = auth.getName();
//		Optional<User> user = userRepository.findByUsername(username);
//		Integer id = user.get().getId();
//		Integer numberOfCommentsPerArticlePerUser = commentsRepository.commentsMaximumTenPerUser(articleId, id);
//		
//		if (numberOfCommentsPerArticlePerUser > 10) {
//			return false;
//		}
//		return true;
//	}
	

}
