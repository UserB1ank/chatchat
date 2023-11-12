package com.example.chatchat.service.image;


import cn.dev33.satoken.util.SaResult;
import freemarker.template.utility.ClassUtil;
import org.springframework.util.ClassUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

//TODO 图片保存问题
public class ImageService {
    private static final String staticPath = System.getProperty("user.dir") + "/src/main/resources/static/";

    public String generateHash(String message) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(message.getBytes());
        byte[] bytes = md.digest();

        // 将字节数组转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public SaResult SaveImage(String img) {
        String path = staticPath + "/images/" + generateHash(img);
        return SaResult.ok(path);
    }

}
