package com.logate.lacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logate.lacademy.domains.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> { }
