package org.example.service;

import java.util.List;

import org.example.entity.StudyResource;
import org.example.entity.Version;
import org.example.model.FileListResponse;
import org.springframework.web.multipart.MultipartFile;

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
    //版本控制相关

    StudyResource previewFileVersion(String fileId, String versionId);

	String deleteFileVersion(String fileId, String versionId);

	byte[] downloadFileVersion(String fileId, String versionId);

	List<Version> getFileVersions(String fileId);

	Version createFileVersion(String fileId, MultipartFile file, String description);
} 