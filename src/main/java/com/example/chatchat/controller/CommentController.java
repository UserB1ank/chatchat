package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Comment;
import com.example.chatchat.service.CRUD.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/getAllStoryComment")
    public List<Comment> getAllStoryComment(@RequestParam(value = "index", defaultValue = "0") Integer index, @RequestParam Integer storyId) {
        return commentService.getAllStoryComment(index, storyId);
    }


    @RequestMapping("/add")
    public SaResult addComment(@RequestParam Integer storyId, @RequestParam String content) {
        return commentService.addCommentToStory(storyId, content);
    }

}
