package com.example.chatchat.data.neo4j.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

public class UserNeo4j {
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Id
    private String account;
    // TODO 询问是否需要这么多关系

    /**
     * 好友关系
     * 发布了哪些Story
     * 发布了哪些Comment
     * 喜欢哪些Story
     * 喜欢哪些Comment
     */

    public UserNeo4j() {
    }

    public UserNeo4j(String account) {
        this.account = account;
    }

    @Relationship(type = "FRIENDS")
    private Set<UserNeo4j> friends;

    public void addFriend(UserNeo4j friend) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend);
    }

    @Relationship(type = "USER_STORY")
    private Set<StoryNeo4j> stories;

    public void addStory(StoryNeo4j story) {
        if (stories == null) {
            stories = new HashSet<>();
        }
        stories.add(story);
    }

    @Relationship(type = "USER_COMMENT")
    private Set<CommentNeo4j> comments;

    public void addComment(CommentNeo4j comment) {
        if (comments == null) {
            comments = new HashSet<>();
        }
        comments.add(comment);
    }

    @Relationship(type = "LIKE_STORY")
    private Set<StoryNeo4j> likeStories;

    public void addLikeStory(StoryNeo4j story) {
        if (likeStories == null) {
            likeStories = new HashSet<>();
        }
        likeStories.add(story);
    }

    @Relationship(type = "LIKE_COMMENT")
    private Set<CommentNeo4j> likeComments;

    public void addLikeComment(CommentNeo4j comment) {
        if (likeComments == null) {
            likeComments = new HashSet<>();
        }
        likeComments.add(comment);
    }
}
