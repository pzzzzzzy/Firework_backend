package org.example.service;

import org.example.entity.Version;
import org.example.repository.VersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {
    
    private static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
    

    
    @Value("${file.upload.dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        try {
            createDirectoryIfNotExists(uploadDir);
        } catch (Exception e) {
            logger.error("Error initializing upload directory", e);
            throw new RuntimeException("Could not initialize upload directory", e);
        }
    }

    private void createDirectoryIfNotExists(String dir) {
        File directory = new File(dir);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + dir);
            }
        }
    }

    public Map<String, Object> saveFile(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;
            
            // 构建文件保存路径
            String filePath = uploadDir + File.separator + newFileName;
            File dest = new File(filePath);
            
            // 保存文件
            file.transferTo(dest);

            
            response.put("code", 200);
            response.put("message", "文件上传成功");
            response.put("data", new HashMap<String, Object>() {{
                put("fileName", newFileName);
                put("originalFileName", originalFilename);
                put("filePath", filePath);
                put("fileSize", file.getSize());
                put("fileType", file.getContentType());
            }});

        } catch (IOException e) {
            logger.error("Error uploading file", e);
            response.put("code", 500);
            response.put("message", "文件上传失败: " + e.getMessage());
            response.put("data", null);
        }

        return response;
    }

   

    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }


}
