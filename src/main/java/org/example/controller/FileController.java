package org.example.controller;

import java.util.List;

import org.example.entity.StudyResource;
import org.example.entity.Version;
import org.example.model.FileListResponse;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    
    /**
     * 获取文件的所有版本列表
     * @param fileId 文件ID
     * @return 文件版本列表
     */
    @GetMapping("/{fileId}/versions")
    public List<Version> getFileVersions(@PathVariable String fileId) {
        return fileService.getFileVersions(fileId);
    }

    /**
     * 创建新版本
     * @param fileId 文件ID
     * @param file 文件对象
     * @param description 版本描述
     * @return 新版本信息
     */
    @PostMapping("/{fileId}/versions")
    public Version createFileVersion(@PathVariable String fileId, 
                                         @RequestParam("file") MultipartFile file, 
                                         @RequestParam("description") String description) {
        return fileService.createFileVersion(fileId, file, description);
    }

    /**
     * 下载特定版本的文件
     * @param fileId 文件ID
     * @param versionId 版本ID
     * @return 文件内容
     */
    @GetMapping("/{fileId}/versions/{versionId}/download")
    public byte[] downloadFileVersion(@PathVariable String fileId, @PathVariable String versionId) {
        return fileService.downloadFileVersion(fileId, versionId);
    }

    /**
     * 删除特定版本
     * @param fileId 文件ID
     * @param versionId 版本ID
     * @return 删除结果
     */
    @DeleteMapping("/{fileId}/versions/{versionId}")
    public String deleteFileVersion(@PathVariable String fileId, @PathVariable String versionId) {
        return fileService.deleteFileVersion(fileId, versionId);
    }

    /**
     * 预览特定版本的文件
     * @param fileId 文件ID
     * @param versionId 版本ID
     * @return 文件预览链接
     */
    @GetMapping("/{fileId}/versions/{versionId}/preview")
    public StudyResource previewFileVersion(@PathVariable String fileId, @PathVariable String versionId) {
        return fileService.previewFileVersion(fileId, versionId);
    }
} 