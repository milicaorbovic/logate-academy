package com.logate.lacademy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.logate.lacademy.domains.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

	//List <Comments> findCommentsByArticle(Integer id);
	
	Optional<Comments> findById(Integer id);
	
	
	
	@Query(value = "select" + " from comments" + " where article_id = id", nativeQuery = true)
	List<Comments> commentsOfSameArticle(@Param("id") Integer id);
	
	@Query(value = "select" + " from comments" + 
			" left join articles on comments.article_id = articles.id" + 
			" where articles.id = id and comments.published_at = Date(now()) and comments.user_id = userId", nativeQuery = true)
	Integer commentsMaximumTenPerUser(@Param("id") Integer id, @Param("id") Integer userId);
}
