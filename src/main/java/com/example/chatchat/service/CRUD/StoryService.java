package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.data.mysql.repository.StoryRepository;
import com.example.chatchat.data.neo4j.model.StoryNeo4j;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.data.neo4j.repository.StoryRepositoryNeo4j;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepositoryMysql;
    @Autowired
    private StoryRepositoryNeo4j storyRepositoryNeo4j;
    @Autowired
    private UserRepositoryNeo4j userRepositoryNeo4j;

    public List<Story> getUserStories() {
        UserNeo4j owner = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());
        List<Story> storyList = new ArrayList<>();
        for (StoryNeo4j story : owner.getStories()) {
            storyList.add(storyRepositoryMysql.getReferenceById(story.getId()));
        }
        return storyList;
    }

    public SaResult addStory(String content, String img) {
        try {
            //增加mysql记录与neo4j节点
            Story newBee = new Story(content, img, StpUtil.getSession().get("account").toString());
            storyRepositoryMysql.save(newBee);
            Integer id = newBee.getId();
            StoryNeo4j newBeeNeo4j = new StoryNeo4j(id);
            storyRepositoryNeo4j.save(newBeeNeo4j);
            //建立关系
            UserNeo4j owner = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());
            owner.addStory(newBeeNeo4j);
            userRepositoryNeo4j.save(owner);

            return SaResult.ok("添加成功");
        } catch (Exception e) {
            // TODO 添加失败，回滚数据，删除node，删除mysql记录
            e.printStackTrace();
            return SaResult.error("添加失败");
        }
    }


    public SaResult deleteStory(Integer id) {
        try {
            UserNeo4j user = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());

            if (storyRepositoryNeo4j.existsById(id) && storyRepositoryMysql.existsById(id)) {
                StoryNeo4j story = storyRepositoryNeo4j.findByid(id);
                if (user.isStoryExist(id)) {
                    storyRepositoryNeo4j.delete(story);
                    storyRepositoryMysql.deleteById(id);
                    return SaResult.ok("删除成功");
                } else {
                    return SaResult.error("删除失败" + story + user.getStories());
                }
            } else {
                return SaResult.error("删除失败" + "1");
            }
        } catch (Exception e) {
            // TODO 删除失败，回滚数据，删除node，删除mysql记录
            e.printStackTrace();
            return SaResult.error("删除失败");
        }
    }
}
