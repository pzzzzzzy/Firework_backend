package org.example.service;

import org.example.entity.Course;
import org.example.entity.StudyResource;
import org.example.exception.ResourceNotFoundException;
import org.example.model.CourseFileResponse;
import org.example.repository.CourseRepository;
import org.example.repository.StudyResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseFileService {

    @Autowired
    private StudyResourceRepository studyResourceRepository;

    @Autowired
    private CourseRepository courseRepository;

    public CourseFileResponse getCourseFiles(Long courseId) {
        // 检查课程是否存在
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }

        // 获取课程的所有文件
        List<StudyResource> resources = studyResourceRepository.findByCourseId(courseId);
        
        // 转换为响应对象
        List<CourseFileResponse.CourseFile> files = resources.stream()
                .map(this::convertToResponseFile)
                .collect(Collectors.toList());

        // 构建响应
        CourseFileResponse response = new CourseFileResponse();
        response.setCode(200);
        response.setMessage("Search successful");
        response.setData(files);

        return response;
    }

    public CourseFileResponse searchFiles(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }

        // 搜索文件
        List<StudyResource> resources = studyResourceRepository.findByTitleContaining(keyword);
        
        // 转换为响应对象
        List<CourseFileResponse.CourseFile> files = resources.stream()
                .map(this::convertToResponseFile)
                .collect(Collectors.toList());

        // 构建响应
        CourseFileResponse response = new CourseFileResponse();
        response.setCode(200);
        response.setMessage("Search successful");
        response.setData(files);

        return response;
    }

    private CourseFileResponse.CourseFile convertToResponseFile(StudyResource resource) {
        CourseFileResponse.CourseFile file = new CourseFileResponse.CourseFile();
        file.setId(resource.getId());
        file.setCourseId(resource.getCourseId());
        file.setTitle(resource.getTitle());
        file.setType(resource.getType());
        file.setSize(resource.getSize());
        file.setUploadTime(resource.getUploadTime().toString());
        file.setDownloadCount(resource.getDownloadCount());
        file.setUrl(resource.getUrl());
        return file;
    }
} 