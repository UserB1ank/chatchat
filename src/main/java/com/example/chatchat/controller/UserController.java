package com.example.chatchat.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.data.mysql.model.User;
import com.example.chatchat.data.neo4j.model.UserNeo4j;
import com.example.chatchat.service.CRUD.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户登录结果
     */
    @RequestMapping("/login")
    public SaResult login(@RequestParam String username, @RequestParam String password) {
        return userService.checkUser(username, password);
    }

    /**
     * 用户注册
     *
     * @param username       用户名
     * @param password       密码
     * @param nickname       昵称
     * @param birthday       出生日期
     * @return 用户注册结果
     */
    @RequestMapping("/register")
    public SaResult register(@RequestParam String username, @RequestParam String password, @RequestParam String nickname, @RequestParam String birthday) {
        return userService.register(username, password, nickname, LocalDate.parse(birthday));
    }

    /**
     * 修改密码
     *
     * @param oldPassword    旧密码
     * @param newPassword    新密码
     * @return 修改密码结果
     */
    @RequestMapping("/changePassword")
    public SaResult changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.changePassword(oldPassword, newPassword);
    }

    /**
     * 更新用户信息
     *
     * @param nickname       昵称
     * @param motto          标语
     * @param avatar         头像
     * @param birthday       出生日期
     * @return 更新用户信息结果
     */
    @RequestMapping("/updateInfo")
    public SaResult updateInfo(@RequestParam String nickname, @RequestParam String motto, @RequestParam String avatar, @RequestParam String birthday) {
        return userService.updateInfo(nickname, motto, avatar, birthday);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @RequestMapping("/getUserInfo")
    public User getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 测试方法
     *
     * @return 当前时间
     */
    @RequestMapping("/test")
    public String test() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.toString();
    }

}
