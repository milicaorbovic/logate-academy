package com.logate.lacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logate.lacademy.domains.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> { }
