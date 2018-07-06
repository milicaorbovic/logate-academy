package com.logate.academy.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.logate.lacademy.domains.Comments;
import com.logate.lacademy.repository.CommentsRepository;

public class CommentsIntegrationTest {
	
	private static final String BASE_API = "http://localhost:8080/api/articles";
	//private static final String AUTH_API = "http://localhost:8080/auth/login";
	
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	CommentsRepository commentRepository;
	
	private Comments createComment()
	{
		Comments comment = new Comments();
		comment.setArticle_id(10);
		comment.setBody("integracioni testovi");
		comment.setPublished_at(new Date());
		comment.setLikes(3);
		comment.setDislikes(4);
		comment.setUser_id(11);
		
		return comment;
	}
	
	private Comments createCommentForList()
	{
		Comments comment = new Comments();
		comment.setArticle_id(11);
		comment.setBody("integracioni testovi / testovi");
		comment.setPublished_at(new Date());
		comment.setLikes(5);
		comment.setDislikes(7);
		comment.setUser_id(12);
		
		return comment;
	}
	
	private Comments createCommentForDelete()
	{
		Comments comment = new Comments();
		comment.setArticle_id(13);
		comment.setBody("integracioni testovi / testovi");
		comment.setPublished_at(new Date());
		comment.setLikes(2);
		comment.setDislikes(8);
		comment.setUser_id(11);
		
		return comment;
	}
	
	private Comments createInvalidComment()
	{
		Comments comment = new Comments();
		comment.setArticle_id(14);
		comment.setUser_id(1);
		
		return comment;
	}
	
	@Test
	public void regularCommentInsertTest() throws Exception
	{
		// get table size before insert...
		int tableSizeBeforeInsert = commentRepository.findAll().size();
		
		// create request body...
		Comments comment = createComment();
		
		HttpEntity<Comments> requestBody = new HttpEntity<>(comment);
		ResponseEntity<Comments> response = testRestTemplate
				.postForEntity(BASE_API, requestBody, Comments.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		int tableSizeAfterInsert = commentRepository.findAll().size();
		assertThat(tableSizeAfterInsert).isGreaterThan(tableSizeBeforeInsert);
	}
	
	@Test
	public void getAllCommentsTest() throws Exception
	{
		Comments comment = createCommentForList();
		Comments dbComment = commentRepository.saveAndFlush(comment);
		
		HttpEntity<Comments> requestBody = new HttpEntity<>(comment);
		ResponseEntity<Comments[]> response = testRestTemplate
				.exchange(BASE_API, HttpMethod.GET, requestBody, Comments[].class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		List<Comments> comments = Arrays.asList(response.getBody()).stream().map(CommentFromList -> {
			return (Comments) CommentFromList;
		}).collect(Collectors.toList());
		
		Comments lastComment = comments.get(comments.size() - 1);
		assertThat(lastComment.getBody()).isEqualTo(dbComment.getBody());
		assertThat(lastComment.getPublished_at()).isEqualTo(dbComment.getPublished_at());
	}
	
	@Test
	public void deleteArticleTest() throws Exception
	{
		Comments comment = createCommentForDelete();
		comment = commentRepository.saveAndFlush(comment);
		
		int tableSizeBeforeDelete = commentRepository.findAll().size();
		
		HttpEntity<Void> requestEntity = new HttpEntity<>();
		ResponseEntity<Void> response = testRestTemplate
				.exchange(BASE_API + "/" + comment.getId(), HttpMethod.DELETE, requestEntity, Void.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		int tableSizeAfterDelete = commentRepository.findAll().size();
		assertThat(tableSizeBeforeDelete).isGreaterThan(tableSizeAfterDelete);
	}
	
	
	@Test
	public void invalidArticleInsertTest() throws Exception
	{
		// get table size before insert...
		int tableSizeBeforeInsert = commentRepository.findAll().size();
		
		// create request body...
		Comments comment = createInvalidComment();
		
		HttpEntity<Comments> requestBody = new HttpEntity<>(comment);
		ResponseEntity<Comments> response = testRestTemplate
				.postForEntity(BASE_API, requestBody, Comments.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		int tableSizeAfterInsert = commentRepository.findAll().size();
		assertThat(tableSizeAfterInsert).isEqualTo(tableSizeBeforeInsert);
	}
}
