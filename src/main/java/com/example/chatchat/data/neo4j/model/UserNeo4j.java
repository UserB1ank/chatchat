
package com.example.chatchat.data.neo4j.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

public class UserNeo4j {
    // 用户账号
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Id
    private String account;

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


    /**
     * 获取用户发布的所有Story
     *
     * @return 用户发布的所有Story对象的集合
     */
    public Set<StoryNeo4j> getStories() {
        return stories;
    }

    @Relationship(type = "USER_STORY")
    private Set<StoryNeo4j> stories;

    /**
     * 添加Story
     *
     * @param story 要添加的Story对象
     */
    public void addStory(StoryNeo4j story) {
        if (stories == null) {
            stories = new HashSet<>();
        }
        stories.add(story);
    }


    /**
     * 判断用户是否已经发布过指定ID的Story
     *
     * @param id 要判断的Story的ID
     * @return 如果用户已经发布过该Story，则返回true；否则返回false
     */
    public boolean isStoryExist(Integer id) {
        if (stories == null) {
            stories = new HashSet<>();
        }
        for (StoryNeo4j storyNeo4j : stories) {
            if (storyNeo4j.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Relationship(type = "USER_COMMENT")
    private Set<CommentNeo4j> comments;

    /**
     * 添加Comment
     *
     * @param comment 要添加的Comment对象
     */
    public void addComment(CommentNeo4j comment) {
        if (comments == null) {
            comments = new HashSet<>();
        }
        comments.add(comment);
    }

    @Relationship(type = "LIKE_STORY")
    private Set<StoryNeo4j> likeStories;

    /**
     * 添加喜欢的Story
     *
     * @param story 要添加的Story对象
     */
    public void addLikeStory(StoryNeo4j story) {
        if (likeStories == null) {
            likeStories = new HashSet<>();
        }
        likeStories.add(story);
    }

    @Relationship(type = "LIKE_COMMENT")
    private Set<CommentNeo4j> likeComments;

    /**
     * 添加喜欢的Comment
     *
     * @param comment 要添加的Comment对象
     */
    public void addLikeComment(CommentNeo4j comment) {
        if (likeComments == null) {
            likeComments = new HashSet<>();
        }
        likeComments.add(comment);
    }

    @Relationship(type = "APPLY_FROM")
    private Set<UserNeo4j> applies;

    /**
     * 添加申请的好友
     *
     * @param user 要添加的申请的好友对象
     */
    public void addApply(UserNeo4j user) {
        if (applies == null) {
            applies = new HashSet<>();
        }
        applies.add(user);
    }

    /**
     * 拒绝好友申请
     *
     * @param account 要拒绝的好友账号
     */
    public boolean refuseApply(String account) {
        if (applies == null) {
            applies = new HashSet<>();
        }
        for (UserNeo4j user : applies) {
            if (user.getAccount().equals(account)) {
                return applies.remove(user);
            }
        }
        return false;
    }

    public boolean isApplyExist(String account) {
        if (applies == null) {
            applies = new HashSet<>();
        }
        for (UserNeo4j user : applies) {
            if (user.getAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }


    public Set<UserNeo4j> getApplyList() {
        return applies;
    }

    //-------------------------------------------

    @Relationship(type = "FRIENDS")
    private Set<UserNeo4j> friends;

    /**
     * 获取好友列表
     *
     * @return 好友列表
     */
    public Set<UserNeo4j> getFriends() {
        return friends;
    }

    /**
     * 添加好友
     *
     * @param friend 要添加的好友对象
     */
    public void addFriend(UserNeo4j friend) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend);
    }

    /**
     * 判断好友是否存在
     *
     * @param account 好友的账号
     * @return 如果好友存在返回true，否则返回false
     */
    public boolean isFriendExist(String account) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        for (UserNeo4j user : friends) {
            if (user.getAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public void removeFriend(UserNeo4j friend) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        for (UserNeo4j user : friends) {
            if (user.getAccount().equals(friend.getAccount())) {
                friends.remove(user);
                return;
            }
        }
    }

}