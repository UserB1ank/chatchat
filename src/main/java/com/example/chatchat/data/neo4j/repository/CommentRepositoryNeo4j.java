package com.example.chatchat.data.neo4j.repository;

import com.example.chatchat.data.neo4j.model.CommentNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CommentRepositoryNeo4j extends Neo4jRepository<CommentNeo4j,Integer> {
    boolean existsByid(Integer id);
}
