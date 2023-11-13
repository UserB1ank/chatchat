package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.data.mysql.repository.StoryRepository;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.data.neo4j.repository.CommentRepositoryNeo4j;
import com.example.chatchat.data.neo4j.repository.StoryRepositoryNeo4j;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    StoryRepository storyRepository;
    @Autowired
    CommentRepositoryNeo4j commentRepositoryNeo4j;
    @Autowired
    UserRepositoryNeo4j userRepositoryNeo4j;
    @Autowired
    StoryRepositoryNeo4j storyRepositoryNeo4j;

    /**
     * 为故事点赞
     */
    public SaResult addLikeForStory(Integer storyId) {
        if (storyId.toString().isEmpty())
            return SaResult.error("故事id不能为空");
        String account = StpUtil.getSession().get("account").toString();
        UserNeo4j userNeo4j = userRepositoryNeo4j.findByAccount(account);
        if (!storyRepository.existsById(storyId) && !storyRepositoryNeo4j.existsById(storyId)) {
            return SaResult.error("该故事不存在");
        }
        if (userNeo4j.isLikeStoryExist(storyId)) {
            return SaResult.error("您已经点赞过该故事");
        } else {
            userNeo4j.addLikeStory(storyRepositoryNeo4j.findByid(storyId));
            Story story = storyRepository.findByid(storyId);
            story.setLikes(story.getLikes() + 1);
            storyRepository.save(story);
            userRepositoryNeo4j.save(userNeo4j);
            return SaResult.ok("点赞成功");
        }
    }

    /**
     * 为评论点赞
     */
}
