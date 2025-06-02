package org.example.controller;

import org.example.model.FileListResponse;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 获取课程的文件列表
     * @param courseId 课程ID
     * @return 文件列表响应
     */
    @GetMapping("/courses/{courseId}/files")
    public FileListResponse getCourseFiles(@PathVariable Long courseId) {
        return fileService.getCourseFiles(courseId);
    }

    /**
     * 搜索文件
     * @param keyword 搜索关键字
     * @return 文件列表响应
     */
    @GetMapping("/search")
    public FileListResponse searchFiles(@RequestParam String keyword) {
        return fileService.searchFiles(keyword);
    }
} 