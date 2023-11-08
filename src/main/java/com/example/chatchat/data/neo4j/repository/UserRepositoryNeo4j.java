package com.example.chatchat.data.neo4j.repository;

import com.example.chatchat.data.neo4j.model.UserNeo4j;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepositoryNeo4j extends Neo4jRepository<UserNeo4j, String> {
    UserNeo4j findByAccount(String username);
    boolean existsByAccount(String username);
    @Query("MATCH (u:UserNeo4j),(s:StoryNeo4j) WHERE u.account = $account and s.id=$id CREATE (u)-[r:USER_STORY]->(p)")
    Relationship addStory(String account, Integer id);
}
