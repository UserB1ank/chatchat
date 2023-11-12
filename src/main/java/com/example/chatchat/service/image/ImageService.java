package com.example.chatchat.service.image;


import cn.dev33.satoken.util.SaResult;
import freemarker.template.utility.ClassUtil;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Set;

//TODO 图片保存问题
public class ImageService {
    private static final String staticPath = System.getProperty("user.dir") + "/src/main/resources/static/";

    private static final Set<String> whiteList = Set.of("jpg", "jpeg", "png", "gif");

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


    public SaResult SaveImage(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        if (Objects.isNull(fileName)) {
            return SaResult.error("图片为空");
        }
        int index = fileName.lastIndexOf('.');
        String suffix = null;
        if (index == -1 || (suffix = fileName.substring(index + 1).toLowerCase()).isEmpty()) {
            return SaResult.error("文件后缀不能为空");
        }
        if (!whiteList.contains(suffix.toLowerCase())) {
            return SaResult.error("非法的图片格式");
        }

        if (file.getSize() > 5 * 1024 * 1024)//大于5M
        {
            SaResult.error("图片大小超过5M");
        }
        String finalPath = staticPath + "/" + path + "/";
        try {
            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(Paths.get(finalPath, generateHash(fileName)), StandardOpenOption.CREATE_NEW));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return SaResult.ok(path);
    }
    //TODO 头像上传
    //TODO 动态图片上传

}
