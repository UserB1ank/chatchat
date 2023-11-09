package com.example.chatchat.data.neo4j.model;

import jakarta.persistence.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoryNeo4j {
    public StoryNeo4j() {
    }

    public StoryNeo4j(Integer id) {
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

    //回复
    /**
     * 故事的回复
     */
    @Relationship(type = "STORY's_REPLIES")
    private Set<CommentNeo4j> storyReplies;

    public Set<CommentNeo4j> getStoryReplies() {
        if (storyReplies == null) {
            storyReplies = new HashSet<>();
        }
        return storyReplies;
    }

    /**
     * 添加回复关系
     *
     * @param reply
     */
    public void addReply(CommentNeo4j reply) {
        if (storyReplies == null) {
            storyReplies = new HashSet<>();
        }
        storyReplies.add(reply);
    }

}
