package com.example.chatchat.data.neo4j.model;

import jakarta.persistence.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

public class StoryNeo4j {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    private int id;

    //回复
    /**
     * 故事的回复
     */
    @Relationship(type = "STORY's_REPLIES")
    public Set<CommentNeo4j> storyReplies;

    public void addReply(CommentNeo4j reply) {
        if (storyReplies == null) {
            storyReplies = new HashSet<>();
        }
        storyReplies.add(reply);
    }

}
