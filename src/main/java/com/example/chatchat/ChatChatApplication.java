package com.example.chatchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// TODO 测试Neo4j能否正常运行
// TODO Redis缓存机制设计
@SpringBootApplication
public class ChatChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatChatApplication.class, args);
    }

}
