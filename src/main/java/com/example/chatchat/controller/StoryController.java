package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.service.CRUD.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @RequestMapping("/add")
    public SaResult addStory(@RequestParam String content, @RequestParam(value = "img", defaultValue = "") String img) {
        return storyService.addStory(content, img);
    }

    @RequestMapping("/delete")
    public SaResult delStory(@RequestParam Integer id) {
        return storyService.deleteStory(id);
    }

    @RequestMapping("/userStory")
    public Set<Story> getUserStories(@RequestParam(value = "index", defaultValue = "0") Integer index) {
        return storyService.getUserStories(index);
    }

    // TODO 动态的排序，热度，时间

    /**
     * 依照用户指定的方式去查询动态
     *
     * @RequestMapping("/getAllStory")
     * @List
     */

    @RequestMapping("/getAllStory")
    public List<Story> getAllStory(@RequestParam(value = "index", defaultValue = "0") Integer index, @RequestParam(value = "type", defaultValue = "likes") StoryService.sortType type) {
        // 根据type参数获取对应的故事列表
        return storyService.getLikesStory(index, type);
    }


}
