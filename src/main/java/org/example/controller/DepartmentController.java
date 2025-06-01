package org.example.controller;

import org.example.entity.Department;
import org.example.model.DepartmentResponse;
import org.example.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        
        List<DepartmentResponse.DepartmentData> departmentDataList = departments.stream()
            .map(department -> {
                List<DepartmentResponse.CourseData> courseDataList = department.getCourses().stream()
                    .map(course -> DepartmentResponse.CourseData.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .date(course.getDate().toString())
                        .build())
                    .collect(Collectors.toList());

                return DepartmentResponse.DepartmentData.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .courses(courseDataList)
                    .build();
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(DepartmentResponse.builder()
            .code(200)
            .message("获取成功")
            .data(departmentDataList)
            .build());
    }
} 