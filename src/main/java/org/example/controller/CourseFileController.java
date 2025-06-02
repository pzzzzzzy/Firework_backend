package org.example.controller;

import org.example.model.CourseFileResponse;
import org.example.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class CourseFileController {

    @Autowired
    private CourseFileService courseFileService;

    @GetMapping("/courses/{courseId}/files")
    public CourseFileResponse getCourseFiles(@PathVariable Long courseId) {
        return courseFileService.getCourseFiles(courseId);
    }
} 