package org.example.service.impl;

import org.example.entity.StudyResource;
import org.example.model.FileListResponse;
import org.example.repository.CourseRepository;
import org.example.repository.StudyResourceRepository;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private StudyResourceRepository studyResourceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public FileListResponse getCourseFiles(Long courseId) {
        // 检查课程是否存在
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        // 获取课程的所有文件
        List<StudyResource> resources = studyResourceRepository.findByCourseId(courseId);

        // 转换为响应格式
        FileListResponse response = new FileListResponse();
        response.setFiles(resources.stream()
                .map(this::convertToFileInfo)
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public FileListResponse searchFiles(String keyword) {
        // 验证关键字不为空
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search keyword cannot be empty");
        }

        // 搜索文件
        List<StudyResource> resources = studyResourceRepository.findByNameContainingIgnoreCase(keyword.trim());

        // 转换为响应格式
        FileListResponse response = new FileListResponse();
        response.setFiles(resources.stream()
                .map(this::convertToFileInfo)
                .collect(Collectors.toList()));

        return response;
    }

    private FileListResponse.FileInfo convertToFileInfo(StudyResource resource) {
        FileListResponse.FileInfo fileInfo = new FileListResponse.FileInfo();
        fileInfo.setId(String.valueOf(resource.getId()));
        fileInfo.setName(resource.getName());
        fileInfo.setSize(resource.getFileSize());
        fileInfo.setType(resource.getFileType());
        fileInfo.setUploadTime(resource.getUploadTime()
                .format(DateTimeFormatter.ISO_DATE_TIME));
        return fileInfo;
    }
} 