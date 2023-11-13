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
import java.util.Objects;
import java.util.Set;

//TODO 图片保存问题
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


    private SaResult SaveImage(MultipartFile file, String path) {
        //TODO 依据文件类型去保存
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
        // TODO 返回相对路径
        return SaResult.ok(fileUrl);
    }

    //TODO 头像上传 单张图片检测
    public SaResult SaveAvatar(MultipartFile file) {
        return SaveImage(file, "avatars");
    }

    //TODO 动态图片上传 多张图片处理
    public SaResult SaveStoryImages(MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                SaveImage(file, "images");
            }
            return SaResult.ok("图片上传成功");
        } catch (Exception e) {
            return SaResult.error("图片上传失败");
        }
    }

}
