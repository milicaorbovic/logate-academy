package com.logate.lacademy.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.logate.lacademy.domains.Article;
import com.logate.lacademy.repository.ArticleRepository;

@Service
public class ArticleService {
	
private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private ArticleRepository articleRepository;
	
	//get all articles
	public Page<Article> getAllArticles(Pageable pageable) {
		return articleRepository.findAll(pageable);
	}
	 //get article by ID
	public Optional<Article> getArticleById(Integer articleId) {
		return articleRepository.findById(articleId);
	}
	
	public Article updateArticle(Article article, Integer id) 
	{
		Optional<Article> articleOptional = articleRepository.findById(id);
		if (articleOptional.isPresent()) 
		{
			Article fetchedArticle = articleOptional.get();
			fetchedArticle.setTitle(article.getTitle());
			fetchedArticle.setPublishedAt(article.getPublishedAt());
			
			return articleRepository.save(fetchedArticle);
		}
		return null;
	}
	
	public Article store(Article article) {
		return articleRepository.save(article);
	}
	
	public void delete(Integer id) {
		articleRepository.deleteById(id);
	}
}
