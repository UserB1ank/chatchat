package com.example.chatchat.data.neo4j.repository;

import com.example.chatchat.data.neo4j.model.UserNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepositoryNeo4j extends Neo4jRepository<UserNeo4j, String> {

}
