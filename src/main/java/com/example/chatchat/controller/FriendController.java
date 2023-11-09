package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.Friend;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.service.CRUD.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 友谊控制器
 */
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private UserService userService;

    /**
     * 申请添加好友
     *
     * @param Account 好友账户
     * @return SaResult对象，包含操作结果信息
     */
    @RequestMapping("/apply")
    public SaResult applyToFriend(@RequestParam String Account) {
        return userService.applyToFriend(Account);
    }

    /**
     * 获取申请列表
     *
     * @param index 页码索引
     * @return 申请列表
     */
    @RequestMapping("/applyList")
    public List<String> geApplyList(@RequestParam(defaultValue = "0", value = "index") Integer index) {
        return userService.getApply(index);
    }

    /**
     * 拒绝申请添加好友
     *
     * @param Account 好友账户
     * @return SaResult对象，包含操作结果信息
     */
    @RequestMapping("/refuseApply")
    public SaResult refuseApply(@RequestParam String Account) {
        return userService.refuseApply(Account);
    }

    /**
     * 同意申请添加好友
     *
     * @param Account 好友账户
     * @return SaResult对象，包含操作结果信息
     */
    @RequestMapping("/agreeApply")
    public SaResult agreeApply(@RequestParam String Account) {
        return userService.agreeApply(Account);
    }

    /**
     * 删除好友
     *
     * @param Account 好友账户
     * @return SaResult对象，包含操作结果信息
     */
    @RequestMapping("/deleteFriend")
    public SaResult deleteFriend(@RequestParam String Account) {
        return userService.deleteFriend(Account);
    }

    /**
     * 获取好友列表
     *
     * @param index 页码索引
     * @return 好友列表
     */
    @RequestMapping("/friendList")
    public Set<Friend> getFriends(@RequestParam(defaultValue = "0", value = "index") Integer index) {
        return userService.getFriends(index);
    }
}
