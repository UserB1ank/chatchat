package com.example.chatchat.data.neo4j.repository;

import com.example.chatchat.data.neo4j.model.StoryNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface StoryRepositoryNeo4j extends Neo4jRepository<StoryNeo4j, Integer> {
}
