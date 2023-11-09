package com.example.chatchat.data.neo4j.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
public class CommentNeo4j {
    public CommentNeo4j() {
    }

    public CommentNeo4j(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    private Integer id;

    /**
     * 需要的关系
     * 回复的回复
     */
    @Relationship(type = "COMMENT's_REPLIES")
    public Set<CommentNeo4j> replies;

    public void addReply(CommentNeo4j reply) {
        if (replies == null) {
            replies = new HashSet<>();
        }
        replies.add(reply);
    }


}


