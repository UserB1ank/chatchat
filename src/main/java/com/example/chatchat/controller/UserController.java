package com.example.chatchat.controller;

import cn.dev33.satoken.util.SaResult;
import com.example.chatchat.service.CRUD.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public SaResult login(@RequestParam String username, @RequestParam String password) {
        return userService.checkUser(username, password);
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}
