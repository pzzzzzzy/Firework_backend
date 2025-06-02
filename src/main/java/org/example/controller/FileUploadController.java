package org.example.controller;

import org.example.entity.Course;
import org.example.entity.StudyResource;
import org.example.repository.CourseRepository;
import org.example.repository.StudyResourceRepository;
import org.example.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    
    @Autowired
    private StudyResourceRepository studyResourceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/chunk")
    public ResponseEntity<Map<String, Object>> uploadChunk(
            @RequestParam("file") MultipartFile file) {
        try {
            // 调用文件上传服务保存文件
            Map<String, Object> uploadResult = fileUploadService.saveFile(file);
            
            if ((int) uploadResult.get("code") != 200) {
                return ResponseEntity.status(500).body(uploadResult);
            }

            // 构建响应
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "文件上传成功");
            response.put("data", uploadResult.get("data"));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "文件上传失败: " + e.getMessage());
            errorResponse.put("data", null);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/merge")
    public ResponseEntity<Map<String, Object>> mergeAndSave(@RequestBody Map<String, Object> data) {
        try {
            // 从请求数据中获取信息
            String fileName = (String) data.get("fileName");
            Long fileSize = Long.valueOf(data.get("fileSize").toString());
            String title = (String) data.get("title");
            String type = (String) data.get("type");
            String category = (String) data.get("category");
            Long courseId = Long.valueOf(data.get("courseId").toString());
            String courseName = (String) data.get("courseName");

            // 获取课程实体
            Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

            // 保存文件信息到数据库
            StudyResource resource = new StudyResource();
            resource.setName(fileName);
            resource.setFileSize(fileSize);
            resource.setFileType(type);  // 使用type字段作为文件类型
            resource.setCourse(course);
            resource.setUploadTime(LocalDateTime.now());

            // 保存到数据库
            StudyResource savedResource = studyResourceRepository.save(resource);

            // 构建响应
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "文件信息保存成功");
            
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("fileId", savedResource.getId());
            responseData.put("fileName", savedResource.getName());
            responseData.put("fileSize", savedResource.getFileSize());
            responseData.put("fileType", savedResource.getFileType());
            responseData.put("uploadTime", savedResource.getUploadTime());
            responseData.put("title", title);
            responseData.put("category", category);
            responseData.put("courseId", savedResource.getCourse().getId());
            responseData.put("courseName", courseName);
            
            response.put("data", responseData);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", e.getStatus().value());
            errorResponse.put("message", e.getReason());
            errorResponse.put("data", null);
            return ResponseEntity.status(e.getStatus()).body(errorResponse);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "文件信息保存失败: " + e.getMessage());
            errorResponse.put("data", null);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
