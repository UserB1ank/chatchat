package com.example.chatchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// TODO Redis缓存机制设计
// TODO 动态推送
// TODO WebSocket聊天室
// TODO 聊天室聊天记录存储
@SpringBootApplication
public class ChatChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatChatApplication.class, args);
    }

}
