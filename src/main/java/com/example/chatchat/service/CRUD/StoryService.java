package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.data.mysql.repository.StoryRepository;
import com.example.chatchat.data.neo4j.model.StoryNeo4j;
import com.example.chatchat.data.neo4j.repository.StoryRepositoryNeo4j;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepositoryMysql;
    @Autowired
    private StoryRepositoryNeo4j storyRepositoryNeo4j;
    @Autowired
    private UserRepositoryNeo4j userRepositoryNeo4j;

    public SaResult addStory(String content, String img) {
        try {
            //增加mysql记录与neo4j节点
            Story newBee = new Story(content, img, StpUtil.getSession().get("account").toString());
            storyRepositoryMysql.save(newBee);
            Integer id = newBee.getId();
            StoryNeo4j newBeeNeo4j = new StoryNeo4j(id);
            storyRepositoryNeo4j.save(newBeeNeo4j);
            //建立关系
            userRepositoryNeo4j.addStory(StpUtil.getSession().get("account").toString(), id);


            return SaResult.ok("添加成功");
        } catch (Exception e) {
            // TODO 添加失败，回滚数据，删除node，删除mysql记录
            return SaResult.error("添加失败");
        }
    }
}
