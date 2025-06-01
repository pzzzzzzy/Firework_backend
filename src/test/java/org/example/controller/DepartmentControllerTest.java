package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.DepartmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllDepartments() throws Exception {
        // 執行獲取部門列表請求
        MvcResult result = mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        DepartmentResponse response = objectMapper.readValue(responseBody, DepartmentResponse.class);
        
        // 驗證響應狀態
        assertEquals(200, response.getCode());
        assertEquals("Success", response.getMessage());
        
        // 驗證數據
        assertNotNull(response.getData());
        assertEquals(3, response.getData().size()); // 應該有3個部門
        
        // 驗證第一個部門的數據
        DepartmentResponse.DepartmentData firstDepartment = response.getData().get(0);
        assertEquals("R&D Department", firstDepartment.getName());
        
        // 驗證第一個部門的所有課程
        assertNotNull(firstDepartment.getCourses());
        assertEquals(2, firstDepartment.getCourses().size(), "R&D Department 應該有2個課程");
        
        // 驗證第一個課程
        DepartmentResponse.CourseData firstCourse = firstDepartment.getCourses().get(0);
        assertEquals("Java Basic Training", firstCourse.getTitle());
        assertEquals("Java programming basic knowledge training", firstCourse.getDescription());
        assertNotNull(firstCourse.getId());
        assertNotNull(firstCourse.getDate());
        
        // 驗證第二個課程
        DepartmentResponse.CourseData secondCourse = firstDepartment.getCourses().get(1);
        assertEquals("Spring Boot Introduction", secondCourse.getTitle());
        assertEquals("Spring Boot framework usage training", secondCourse.getDescription());
        assertNotNull(secondCourse.getId());
        assertNotNull(secondCourse.getDate());
    }

    @Test
    public void testGetDepartmentCourses() throws Exception {
        // 執行獲取單個部門課程列表請求
        MvcResult result = mockMvc.perform(get("/api/departments/1/courses"))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        DepartmentResponse.DepartmentData department = objectMapper.readValue(responseBody, DepartmentResponse.DepartmentData.class);
        
        // 驗證部門數據
        assertEquals("R&D Department", department.getName());
        
        // 驗證課程列表
        assertNotNull(department.getCourses());
        assertEquals(2, department.getCourses().size(), "R&D Department 應該有2個課程");
        
        // 驗證第一個課程
        DepartmentResponse.CourseData firstCourse = department.getCourses().get(0);
        assertEquals("Java Basic Training", firstCourse.getTitle());
        assertEquals("Java programming basic knowledge training", firstCourse.getDescription());
        assertNotNull(firstCourse.getId());
        assertNotNull(firstCourse.getDate());
        
        // 驗證第二個課程
        DepartmentResponse.CourseData secondCourse = department.getCourses().get(1);
        assertEquals("Spring Boot Introduction", secondCourse.getTitle());
        assertEquals("Spring Boot framework usage training", secondCourse.getDescription());
        assertNotNull(secondCourse.getId());
        assertNotNull(secondCourse.getDate());
    }

    @Test
    public void testGetDepartmentCoursesNotFound() throws Exception {
        // 測試不存在的部門ID
        mockMvc.perform(get("/api/departments/999/courses"))
                .andExpect(status().isNotFound());
    }
} 