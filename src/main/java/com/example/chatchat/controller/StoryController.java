package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.service.CRUD.StoryService;
import com.example.chatchat.service.CRUD.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @RequestMapping("/add")
    public SaResult addStory(@RequestParam String content, @RequestParam String img) {
        return storyService.addStory(content, img);
    }

    @RequestMapping("/delete")
    public SaResult delStory(@RequestParam Integer id) {
        return storyService.deleteStory(id);
    }

    @RequestMapping("/userStory")
    public List<Story> getUserStories() {
        return storyService.getUserStories();
    }

    // TODO 如何限定获取的动态的数量
    // TODO 动态的排序，热度，时间

    /**
     * 依照用户指定的方式去查询动态
     *
     * @RequestMapping("/getAllStory")
     * @List
     */

    public enum sortType {
        LIKES,
        DATE
    }
    @RequestMapping("/getAllStory")
    public List<Story> getAllStory(@RequestParam sortType type) {
        // 根据type参数获取对应的故事列表
        return null;

    }


}
