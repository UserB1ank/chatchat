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

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private UserService userService;

    @RequestMapping("/apply")
    public SaResult applyToFriend(@RequestParam String Account) {
        return userService.applyToFriend(Account);
    }

    @RequestMapping("/applyList")
    public List<String> geApplyList(@RequestParam(defaultValue = "0", value = "index") Integer index) {
        return userService.getApply(index);
    }

    @RequestMapping("/refuseApply")
    public SaResult refuseApply(@RequestParam String Account) {
        return userService.refuseApply(Account);
    }

    @RequestMapping("/agreeApply")
    public SaResult agreeApply(@RequestParam String Account) {
        return userService.agreeApply(Account);
    }

    @RequestMapping("/deleteFriend")
    public SaResult deleteFriend(@RequestParam String Account) {
        return userService.deleteFriend(Account);
    }

    @RequestMapping("/friendList")
    public Set<Friend> getFriends(@RequestParam(defaultValue = "0", value = "index") Integer index) {
        return userService.getFriends(index);
    }

}
