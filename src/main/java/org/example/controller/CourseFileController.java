package org.example.controller;

import org.example.model.CourseFileResponse;
import org.example.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class CourseFileController {

    @Autowired
    private CourseFileService courseFileService;

    @GetMapping("/courses/{courseId}/files")
    public ResponseEntity<CourseFileResponse> getCourseFiles(@PathVariable Long courseId) {
        CourseFileResponse response = courseFileService.getCourseFiles(courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<CourseFileResponse> searchFiles(@RequestParam String keyword) {
        try {
            CourseFileResponse response = courseFileService.searchFiles(keyword);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 