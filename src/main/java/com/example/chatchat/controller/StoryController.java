package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.Story;
import com.example.chatchat.service.CRUD.StoryService;
import com.example.chatchat.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * 故事控制器
 */
@RestController
@RequestMapping("/story")
public class StoryController {

    @Autowired
    private StoryService storyService;
    @Autowired
    private ImageService imageService;

    /**
     * 添加故事
     *
     * @param content 故事内容
     * @param img     故事图片，默认为空
     * @return 故事添加结果
     */
    @RequestMapping("/add")
    //TODO 多图上传，返回路径获取
    public SaResult addStory(@RequestParam String content, @RequestPart(required = false) MultipartFile[] img) {
        List<String> imgs = null;
        if (img != null && img.length > 0) {
            System.out.println("haha");
            imgs = (List<String>) imageService.SaveStoryImages(img).getData();
            System.out.println("bad");
        }
        return storyService.addStory(content, imgs);
//        return null;
    }

    /**
     * 删除故事
     *
     * @param id 故事ID
     * @return 故事删除成功与否的结果
     */
    @RequestMapping("/delete")
    public SaResult delStory(@RequestParam Integer id) {
        return storyService.deleteStory(id);
    }

    /**
     * 获取用户故事
     *
     * @param index 用户故事的索引
     * @return 用户故事集合
     */
    @RequestMapping("/userStory")
    public Set<Story> getUserStories(@RequestParam(value = "index", defaultValue = "0") Integer index) {
        return storyService.getUserStories(index);
    }

    // TODO 动态的排序，热度，时间

    /**
     * 获取全部故事
     *
     * @param index 故事的索引
     * @param type  故事的排序类型 likes,createDate 按like的数量或创建日期排序
     * @return 故事集合
     */
    @RequestMapping("/getAllStory")
    public List<Story> getAllStory(@RequestParam(value = "index", defaultValue = "0") Integer index, @RequestParam(value = "type", defaultValue = "likes") StoryService.sortType type) {
        // 根据type参数获取对应的故事列表
        return storyService.getLikesStory(index, type);
    }

}
