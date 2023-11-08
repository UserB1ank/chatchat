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

    @RequestMapping("/userstory")
    public List<Story> getUserStories() {
        return storyService.getUserStories();
    }
}
