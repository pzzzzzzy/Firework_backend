package org.example.service;

import org.example.model.FileListResponse;

public interface FileService {
    /**
     * 获取课程的文件列表
     * @param courseId 课程ID
     * @return 文件列表响应
     */
    FileListResponse getCourseFiles(Long courseId);

    /**
     * 搜索文件
     * @param keyword 搜索关键字
     * @return 文件列表响应
     */
    FileListResponse searchFiles(String keyword);
} 