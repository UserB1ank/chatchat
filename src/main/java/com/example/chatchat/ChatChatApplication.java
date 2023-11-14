package com.example.chatchat;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import io.github.yedaxia.apidocs.plugin.markdown.MarkdownDocPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.print.Doc;

// TODO Redis缓存机制设计
// TODO 动态推送
// TODO WebSocket聊天室
// TODO 聊天室聊天记录存储
// TODO 删除评论/故事时同步删除图片
@SpringBootApplication
public class ChatChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatChatApplication.class, args);
//        DocsConfig config =new DocsConfig();
//        config.setProjectPath("/home/userb1ank/workspace/java/ChatChat/"); // 项目根目录
//        config.setProjectName("ChatChat");    // 项目名称
//        config.setApiVersion("V1.1");       // 声明该API的版本
//        config.setDocsPath("/home/userb1ank/Documents/ChatChat"); // 生成API 文档所在目录
//        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
//        config.addPlugin(new MarkdownDocPlugin()); // 生成MarkDown
//        Docs.buildHtmlDocs(config); // 执行生成文档
    }

}
