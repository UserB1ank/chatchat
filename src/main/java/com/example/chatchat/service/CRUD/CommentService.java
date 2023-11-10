package com.example.chatchat.service.CRUD;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Comment;
import com.example.chatchat.data.mysql.repository.CommentRepository;
import com.example.chatchat.data.neo4j.model.CommentNeo4j;
import com.example.chatchat.data.neo4j.model.StoryNeo4j;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.data.neo4j.repository.CommentRepositoryNeo4j;
import com.example.chatchat.data.neo4j.repository.StoryRepositoryNeo4j;
import com.example.chatchat.data.neo4j.repository.UserRepositoryNeo4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepositoryMysql;
    @Autowired
    private CommentRepositoryNeo4j commentRepositoryNeo4j;
    @Autowired
    private StoryRepositoryNeo4j storyRepositoryNeo4j;
    @Autowired
    private UserRepositoryNeo4j userRepositoryNeo4j;


    /**
     * 获取动态的全部回复
     *
     * @param index   页码
     * @param storyId 动态id
     * @return 回复列表
     */

    public List<Comment> getAllStoryComment(Integer index, Integer storyId) {
        //先从neo4j中查找这个story的所有回复，然后排序，然后分页
        StoryNeo4j story = storyRepositoryNeo4j.findByid(storyId);
        List<Comment> commentList = new ArrayList<>();
        for (CommentNeo4j commentNeo4j : story.getStoryReplies()) {
            //从中获取comment的id，再获取comment对象
            Comment comment = commentRepositoryMysql.findByid(commentNeo4j.getId());
            commentList.add(comment);
        }
        return commentList.subList(index <= 0 ? 0 : --index, Math.min(index + 10, commentList.size()));
    }

    /**
     * 添加回复
     *
     * @param storyId 被回复的动态
     * @param content 回复的内容
     * @return 添加结果
     */

    public SaResult addCommentToStory(Integer storyId, String content) {
        if (storyId.toString().isEmpty()) {
            return SaResult.error("storyId不能为空");
        }
        if (commentRepositoryMysql.existsById(storyId)) {
            return SaResult.error("story不存在");
        }
        if (content.isEmpty()) {
            return SaResult.error("评论内容不能为空");
        }
        if (content.length() > 200) {
            return SaResult.error("评论内容过长");
        }
        //评论发起人为当前用户
        String owner = StpUtil.getSession().get("account").toString();
        //生成mysql记录
        Comment comment = new Comment(owner, content);
        commentRepositoryMysql.save(comment);
        //生成neo4j记录
        CommentNeo4j commentNeo4j = new CommentNeo4j(comment.getId());
        commentRepositoryNeo4j.save(commentNeo4j);
        //添加与story的关系
        //获取story目标
        StoryNeo4j story = storyRepositoryNeo4j.findByid(storyId);
        story.addReply(commentNeo4j);
        //保存关系
//        storyRepositoryNeo4j.delete(story);
        storyRepositoryNeo4j.save(story);
        return SaResult.ok();
    }

    /**
     * 删除评论
     * @param id 评论id
     * @return 删除结果
     */
    public SaResult OwnDeleteComment(Integer id) {
        String owner = StpUtil.getSession().get("account").toString();
        if (userRepositoryNeo4j.findByAccount(owner).isStoryExist(id)) {
            return SaResult.error("你不能删除别人的评论");
        }
        if (id.toString().isEmpty()) {
            return SaResult.error("id不能为空");
        }
        if (!commentRepositoryNeo4j.existsByid(id) || !commentRepositoryMysql.existsByid(id)) {
            return SaResult.error("评论不存在");
        }
        commentRepositoryMysql.deleteById(id);
        commentRepositoryNeo4j.deleteById(id);
        return SaResult.ok();
    }

    public SaResult updateComment(Integer id, String content) {
        if (id.toString().isEmpty()) {
            return SaResult.error("id不能为空");
        }
        if (content.isEmpty()) {
            return SaResult.error("评论内容不能为空");
        }
        if (content.length() > 200) {
            return SaResult.error("评论内容过长");
        }
        if (!commentRepositoryMysql.findByid(id).getOwner().equals(StpUtil.getSession().get("account").toString())){
            return SaResult.error("你不能修改别人的评论");
        }
        Comment comment = commentRepositoryMysql.findByid(id);
        comment.setContent(content);
        commentRepositoryMysql.save(comment);
        return SaResult.ok();
    }
}
