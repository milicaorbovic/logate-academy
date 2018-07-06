package com.logate.academy.integration.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.logate.lacademy.domains.Article;
import com.logate.lacademy.domains.Document;
import com.logate.lacademy.domains.Employee;
import com.logate.lacademy.repository.ArticleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = { "dev" })
public class ArticlesIntegrationTest {
	
	private static final String BASE_API = "http://localhost:8080/api/articles";
	//private static final String AUTH_API = "http://localhost:8080/auth/login";
	
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@PostConstruct
	public void setup() {
		testRestTemplate = new TestRestTemplate();
	}
	
	
	
	private Article createArticle()
	{
		Document document = new Document();
		Employee employee = new Employee();
		Article article = new Article();
		article.setTitle("Clanak1");
		article.setEmployee(employee);//id
		article.setPublishedAt(new Date());
		article.setDocument(document);//id
		
		return article;
	}
	
	private Article createArticleForDelete()
	{
		Document document = new Document();
		Employee employee = new Employee();
		Article article = new Article();
		article.setTitle("Clanak1");
		article.setEmployee(employee);//id
		article.setPublishedAt(new Date());
		article.setDocument(document);//id
		
		return article;
	}
	
	private Article createArticleForList()
	{
		Document document = new Document();
		Employee employee = new Employee();
		Article article = new Article();
		article.setTitle("Clanak1");
		article.setEmployee(employee);//id
		article.setPublishedAt(new Date());
		article.setDocument(document);//id
		
		return article;
	}
	
	private Article createInvalidArticle()
	{
		Document document = new Document();
		Article article = new Article();
		article.setId(2);
		article.setDocument(document);//id
		
		return article;
	}
	
	@Test
	public void regularArticleInsertTest() throws Exception
	{
		// get table size before insert...
		int tableSizeBeforeInsert = articleRepository.findAll().size();
		
		// create request body...
		Article article = createArticle();
		
		HttpEntity<Article> requestBody = new HttpEntity<>(article);
		ResponseEntity<Article> response = testRestTemplate
				.postForEntity(BASE_API, requestBody, Article.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		int tableSizeAfterInsert = articleRepository.findAll().size();
		assertThat(tableSizeAfterInsert).isGreaterThan(tableSizeBeforeInsert);
	}
	
	@Test
	public void getAllArticlesTest() throws Exception
	{
		Article article = createArticleForList();
		Article dbArticle = articleRepository.saveAndFlush(article);
		
		HttpEntity<Article> requestBody = new HttpEntity<>(article);
		ResponseEntity<Article[]> response = testRestTemplate
				.exchange(BASE_API, HttpMethod.GET, requestBody, Article[].class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		List<Article> articles = Arrays.asList(response.getBody()).stream().map(ArticleFromList -> {
			return (Article) ArticleFromList;
		}).collect(Collectors.toList());
		
		Article lastArticle = articles.get(articles.size() - 1);
		assertThat(lastArticle.getTitle()).isEqualTo(dbArticle.getTitle());
		assertThat(lastArticle.getPublishedAt()).isEqualTo(dbArticle.getPublishedAt());
	}
	
	@Test
	public void deleteArticleTest() throws Exception
	{
		Article article = createArticleForDelete();
		article = articleRepository.saveAndFlush(article);
		
		int tableSizeBeforeDelete = articleRepository.findAll().size();
		
		HttpEntity<Void> requestEntity = new HttpEntity<>();
		ResponseEntity<Void> response = testRestTemplate
				.exchange(BASE_API + "/" + article.getId(), HttpMethod.DELETE, requestEntity, Void.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		int tableSizeAfterDelete = articleRepository.findAll().size();
		assertThat(tableSizeBeforeDelete).isGreaterThan(tableSizeAfterDelete);
	}
	
	
	@Test
	public void invalidArticleInsertTest() throws Exception
	{
		// get table size before insert...
		int tableSizeBeforeInsert = articleRepository.findAll().size();
		
		// create request body...
		Article article = createInvalidArticle();
		
		HttpEntity<Article> requestBody = new HttpEntity<>(article);
		ResponseEntity<Article> response = testRestTemplate
				.postForEntity(BASE_API, requestBody, Article.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		
		int tableSizeAfterInsert = articleRepository.findAll().size();
		assertThat(tableSizeAfterInsert).isEqualTo(tableSizeBeforeInsert);
	}
	
	
}
