package com.logate.lacademy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.logate.lacademy.domains.Comments;
import com.logate.lacademy.domains.User;
import com.logate.lacademy.repository.CommentsRepository;
import com.logate.lacademy.repository.UserRepository;

@Service
public class CommentsService {
	
	@Autowired
	private CommentsRepository commentsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Page<Comments> getAllComments(Pageable pageable) {
		return commentsRepository.findAll(pageable);
	}

	public Optional<Comments> getCommentById(Integer commentId) {
		return commentsRepository.findById(commentId);
	}
	
	public Comments store(Comments comment) {
		return commentsRepository.save(comment);
	}
	
	public Comments updateComments(Comments comment, Integer id) 
	{
		SecurityContext secCon = SecurityContextHolder.getContext();
		Authentication auth = secCon.getAuthentication();
		String username = auth.getName();
		Optional<User> user = userRepository.findByUsername(username);
		Integer loggedInUser = 0;
		if (user.isPresent()) {
			loggedInUser = user.get().getId();
		}
		Optional<Comments> commentOptional = commentsRepository.findById(id);
		if (commentOptional.isPresent()) 
			if(loggedInUser == commentOptional.get().getUser_id())
			{
				{
					Comments fetchedComment = commentOptional.get();
					fetchedComment.setBody(comment.getBody());
					fetchedComment.setPublished_at(comment.getPublished_at());
					
					return commentsRepository.save(fetchedComment);
				}
			}
		return null;
	}
	
	public void delete(Integer id) {
		SecurityContext secCon = SecurityContextHolder.getContext();
		Authentication auth = secCon.getAuthentication();
		String username = auth.getName();
		Optional<User> user = userRepository.findByUsername(username);
		Integer loggedInUser = 0;
		if (user.isPresent()) {
			loggedInUser = user.get().getId();
		}
		Optional<Comments> commentOptional = commentsRepository.findById(id);
		if (commentOptional.isPresent()) 
			if(loggedInUser == commentOptional.get().getUser_id())
			{
				commentsRepository.deleteById(id);
			}
	}

//	public void delete() {
//		commentsRepository.deleteAll();
//		
//	}
	
	public void deleteByArticleId(Integer id) {
		//List<Comments> comments = new ArrayList<>();
		List<Comments> comments = commentsRepository.commentsOfSameArticle(id);
		for (int i = 0; i <= comments.size(); i++)
		{
			Integer index = comments.get(i).getId();
			commentsRepository.deleteById(index);
		}
	}
	public Comments updateLikes(Integer id) 
	{
		Optional<Comments> commentOptional = commentsRepository.findById(id);
		
		if (commentOptional.isPresent()) 
		{
			Comments fetchedComment = commentOptional.get();
			fetchedComment.setLikes(fetchedComment.getLikes()+1);
			
			return commentsRepository.save(fetchedComment);
		}
		return null;
	}
	
	public Comments updateDislikes(Integer id) 
	{
		Optional<Comments> commentOptional = commentsRepository.findById(id);
		if (commentOptional.isPresent()) 
		{
			Comments fetchedComment = commentOptional.get();
			fetchedComment.setDislikes(fetchedComment.getDislikes()+1);
			
			return commentsRepository.save(fetchedComment);
		}
		return null;
	}
	
	public boolean checkComments(Integer articleId) {
		SecurityContext secCon = SecurityContextHolder.getContext();
		Authentication auth = secCon.getAuthentication();
		String username = auth.getName();
		Optional<User> user = userRepository.findByUsername(username);
		Integer id = 0;
		if (user.isPresent()) {
			id = user.get().getId();
		}
		Integer numberOfCommentsPerArticlePerUser = commentsRepository.commentsMaximumTenPerUser(articleId, id);
		
		if (numberOfCommentsPerArticlePerUser > 10) {
			return false;
		}
		return true;
	}
}
