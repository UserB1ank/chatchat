package com.example.chatchat.service.image;


import cn.dev33.satoken.util.SaResult;
import freemarker.template.utility.ClassUtil;
import org.springframework.stereotype.Service;
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
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
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
    public SaResult SaveAvatar(MultipartFile file) {
        String path = "avatars";
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
        String finalFileName = generateHash(fileName) + fileName.substring(fileName.lastIndexOf('.'));
        String finalPath = staticPath + "/" + path + "/";
        try {
            FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(Paths.get(finalPath, finalFileName), StandardOpenOption.CREATE_NEW));
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        String fileUrl = path + "/" + finalFileName;
        return SaResult.ok(fileUrl);
    }

    public SaResult SaveStoryImages(MultipartFile[] files) {
        String path = "images";
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
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
            String finalFileName = generateHash(fileName + LocalDateTime.now()) + fileName.substring(fileName.lastIndexOf('.'));
            String finalPath = staticPath + "/" + path + "/";
            try {
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(Paths.get(finalPath, finalFileName), StandardOpenOption.CREATE_NEW));
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
            String fileUrl = path + "/" + finalFileName;
            paths.add(fileUrl);
        }
        return SaResult.data(paths);
    }

}
