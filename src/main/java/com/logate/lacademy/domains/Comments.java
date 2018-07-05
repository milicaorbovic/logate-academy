package com.logate.lacademy.domains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Description;

@Description(value = "Comments Entity Representation.")
@Entity
@Table(name = "comments")
public class Comments implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private Integer article_id;
	
//	@ManyToOne
//	@JoinColumn(name = "article_id")
//	@JsonBackReference
//	private Article article;
	
	@Column(name = "body", nullable = false)
	private String body;
	
	@Column(name = "published_at", nullable = false)
	private Date published_at;
	
	@Column(name = "likes", nullable = false)
	private Integer likes;
	
	@Column(name = "dislikes", nullable = false)
	private Integer dislikes;
	
	@Column(name = "user_id", nullable = false)
	private Integer user_id;

	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * @return the article_id
	 */
	public Integer getArticle_id() {
		return article_id;
	}


	/**
	 * @param article_id the article_id to set
	 */
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}


	/**
	 * @return the published_at
	 */
	public Date getPublished_at() {
		return published_at;
	}


	/**
	 * @param published_at the published_at to set
	 */
	public void setPublished_at(Date published_at) {
		this.published_at = published_at;
	}


	/**
	 * @return the likes
	 */
	public Integer getLikes() {
		return likes;
	}
	
	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Integer likes) {
		this.likes = likes;
	}


	/**
	 * @return the dislikes
	 */
	public Integer getDislikes() {
		return dislikes;
	}


	/**
	 * @param dislikes the dislikes to set
	 */
	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}


	/**
	 * @return the user_id
	 */
	public Integer getUser_id() {
		return user_id;
	}


	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	

	@Override
	public String toString() {
		return "Comments [id=" + id + " content:" + body + ", publishedAt=" + published_at + "]";
	}
}
