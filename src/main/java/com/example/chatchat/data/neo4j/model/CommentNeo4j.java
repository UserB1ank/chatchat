package com.example.chatchat.data.neo4j.model;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
public class CommentNeo4j {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    @Id
    private int id;
    private String owner;

    /**需要的关系
     *  回复的回复
     *
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


