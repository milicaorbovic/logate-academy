package com.logate.lacademy.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.logate.lacademy.domains.Comments;
import com.logate.lacademy.services.CommentsService;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class CommentsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private CommentsService commentsService;
	
	//kreiranje komentara
	@PreAuthorize("@authComponent.hasHeaderPermission(#authHeader)")
	@RequestMapping(value = "/comments", 
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comments> createNew(@Valid @RequestBody Comments comments)
	{
		boolean c = commentsService.checkComments(comments.getArticle_id());
		if (c == true) {
			Comments dbComments = commentsService.store(comments);
			return new ResponseEntity<>(dbComments, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(comments, HttpStatus.NOT_FOUND);
			
	}
	
	@RequestMapping(value = "/comments/{id}", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comments> updateComments(@PathVariable(value = "id") Integer id,
										   @RequestBody Comments comments)
	{
		if (comments.getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Comments updatedComment = commentsService.updateComments(comments, id);
		return Optional.ofNullable(updatedComment)
				.map(comm -> new ResponseEntity<>(comm, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@RequestMapping(value = "/comments/{id}", 
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") Integer id)
	{
		commentsService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/articles/{id}/comments", 
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteCommentsByArticleId(@PathVariable(value = "id") Integer id)
	{
		commentsService.deleteByArticleId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/comments/{id}/likes", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comments> updatedLikes(@PathVariable(value = "id") Integer id,
										   @RequestBody Comments comments)
	{
		if (comments.getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (comments.getLikes() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Comments updatedComment = commentsService.updateLikes(id);
		return Optional.ofNullable(updatedComment)
				.map(comm -> new ResponseEntity<>(comm, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	
	@RequestMapping(value = "/comments/{id}/dislikes", 
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Comments> updateDislikes(@PathVariable(value = "id") Integer id,
										   @RequestBody Comments comments)
	{
		if (comments.getId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (comments.getDislikes() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Comments updatedComment = commentsService.updateDislikes(id);
		return Optional.ofNullable(updatedComment)
				.map(comm -> new ResponseEntity<>(comm, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
