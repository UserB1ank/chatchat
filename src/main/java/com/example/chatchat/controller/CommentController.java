package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Comment;
import com.example.chatchat.service.CRUD.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注释：评论控制器
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    // TODO 点赞机制
    @Autowired
    private CommentService commentService;

    /**
     * 注释：获取所有故事的评论
     * @param index 分页索引
     * @param storyId 故事ID
     * @return 所有故事的评论列表
     */
    @RequestMapping("/getAllStoryComment")
    public List<Comment> getAllStoryComment(@RequestParam(value = "index", defaultValue = "0") Integer index, @RequestParam Integer storyId) {
        return commentService.getAllStoryComment(index, storyId);
    }

    /**
     * 注释：添加评论到故事
     * @param storyId 故事ID
     * @param content 评论内容
     * @return 添加评论的结果
     */
    @RequestMapping("/addComment")
    public SaResult addComment(@RequestParam Integer storyId, @RequestParam String content) {
        return commentService.addCommentToStory(storyId, content);
    }

    /**
     * 注释：删除自己的评论
     * @param id 评论ID
     * @return 删除评论的结果
     */
    @RequestMapping("/deleteOwnComment")
    public SaResult deleteComment(@RequestParam Integer id) {
        return commentService.OwnDeleteComment(id);
    }

    /**
     * 注释：更新评论内容
     * @param id 评论ID
     * @param content 新的评论内容
     * @return 更新评论的结果
     */
    @RequestMapping("/updateComment")
    public SaResult updateComment(@RequestParam Integer id, @RequestParam String content) {
        return commentService.updateComment(id, content);
    }

}
