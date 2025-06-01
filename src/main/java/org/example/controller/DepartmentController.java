package org.example.controller;


import org.example.entity.Department;
import org.example.model.DepartmentResponse;
import org.example.entity.Course;
import org.example.entity.Department;
import org.example.model.DepartmentResponse;
import org.example.repository.CourseRepository;
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


    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponse.DepartmentData> departmentDataList = departments.stream()
            .map(department -> {
                List<Course> courses = courseRepository.findByDepartmentId(department.getId());
                List<DepartmentResponse.CourseData> courseDataList = courses.stream()
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
            .message("Success")
            .data(departmentDataList)
            .build());
    }

    @GetMapping("/{departmentId}/courses")
    public ResponseEntity<?> getDepartmentCourses(@PathVariable Long departmentId) {
        return departmentRepository.findById(departmentId)
            .map(department -> {
                List<Course> courses = courseRepository.findByDepartmentId(departmentId);
                List<DepartmentResponse.CourseData> courseDataList = courses.stream()
                    .map(course -> DepartmentResponse.CourseData.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .date(course.getDate().toString())
                        .build())
                    .collect(Collectors.toList());

                DepartmentResponse.DepartmentData departmentData = DepartmentResponse.DepartmentData.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .courses(courseDataList)
                    .build();

                return ResponseEntity.ok(departmentData);
            })
            .orElse(ResponseEntity.notFound().build());
    }
} 


//測試獲取全部部門及課程，驗證數據正確性。
//測試獲取單一部門課程，驗證課程內容與數量。
//測試不存在部門時的 404 行為
