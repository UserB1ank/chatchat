package com.example.chatchat.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
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

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public SaResult login(@RequestParam String username, @RequestParam String password) {
        return userService.checkUser(username, password);
    }

    @RequestMapping("/register")
    public SaResult register(@RequestParam String username, @RequestParam String password, @RequestParam String nickname, @RequestParam String birthday) {
        return userService.register(username, password, nickname, LocalDate.parse(birthday));
    }

    @RequestMapping("/apply")
    public SaResult applyToFriend(@RequestParam String friendAccount) {
        return userService.applyToFriend(friendAccount);
    }


    @RequestMapping("/test")
    public String test() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.toString();
    }

}
