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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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


    // TODO 分页查询
    public Set<Story> getUserStories(Integer index) {
        String account = StpUtil.getSession().get("account").toString();
        PageRequest pageRequest = PageRequest.of(index <= 0 ? 0 : --index, 10);
        return storyRepositoryMysql.findAllByOwner(account, pageRequest);
    }

    public SaResult addStory(String content, List<String> img) {
        try {
            StringBuilder img_data = new StringBuilder();
            //处理图片
            if (!img.isEmpty()) {
                for (String uri : img) {
                    img_data.append(uri).append(",");
                }
            }
            //增加mysql记录与neo4j节点
            Story newBee = new Story(content, img_data.toString(), StpUtil.getSession().get("account").toString());
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

    // Todo 几种不同的获取动态的方式
    // Todo 动态获取后，由谁发布的动态，这个信息如何获取并传递到前端
    public Set<Story> getFriendStories() {
        UserNeo4j owner = userRepositoryNeo4j.findByAccount(StpUtil.getSession().get("account").toString());
        Set<Story> storyList = new HashSet<>();
        for (UserNeo4j friend : owner.getFriends()) {
            for (StoryNeo4j Story : friend.getStories()) {
                Story story = storyRepositoryMysql.getReferenceById(Story.getId());
                storyList.add(story);
            }
        }
        return storyList;
    }

    public enum sortType {
        likes,
        createDate,
    }

    /**
     * 获取点赞最多的动态
     *
     * @param index 点赞数排序索引，默认为0，即从最近的点赞数开始排序
     * @param type  点赞数排序类型，包括点赞数从高到低和点赞数从低到高
     * @return 获取到的动态列表，按照点赞数从高到低排序
     */
    public List<Story> getLikesStory(Integer index, sortType type) {
        PageRequest pageRequest = PageRequest.of(index <= 0 ? 0 : --index, 10);
        return storyRepositoryMysql.findAllSortedBy(Sort.by(Sort.Direction.DESC, type.toString()), pageRequest);
    }

}
