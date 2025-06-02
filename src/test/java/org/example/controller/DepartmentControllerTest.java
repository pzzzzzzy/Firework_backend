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
        assertEquals(4, response.getData().size()); // 應該有4個部門
        
        // 驗證第一個部門的數據
        DepartmentResponse.DepartmentData firstDepartment = response.getData().get(0);
<<<<<<< HEAD
        assertEquals("Development", firstDepartment.getName());
        
        // 驗證第一個部門的所有課程
        assertNotNull(firstDepartment.getCourses());
        assertEquals(2, firstDepartment.getCourses().size(), "Development Department 應該有2個課程");
        
        // 驗證第一個課程
        DepartmentResponse.CourseData firstCourse = firstDepartment.getCourses().get(0);
        assertEquals("Java Basics", firstCourse.getTitle());
        assertEquals("Basic Java Programming Training", firstCourse.getDescription());
=======
        assertEquals("Computer Science Department", firstDepartment.getName());
        
        // 驗證第一個部門的所有課程
        assertNotNull(firstDepartment.getCourses());
        assertEquals(4, firstDepartment.getCourses().size(), "Computer Science Department 應該有4個課程");
        
        // 驗證第一個課程
        DepartmentResponse.CourseData firstCourse = firstDepartment.getCourses().get(0);
        assertEquals("Data Structures and Algorithms", firstCourse.getTitle());
        assertEquals("Fundamental data structures and algorithm design", firstCourse.getDescription());
>>>>>>> aa25a8ba9dffd68683ff7b8108aa78fda4051845
        assertNotNull(firstCourse.getId());
        assertNotNull(firstCourse.getDate());
        
        // 驗證第二個課程
        DepartmentResponse.CourseData secondCourse = firstDepartment.getCourses().get(1);
<<<<<<< HEAD
<<<<<<< HEAD
=======
        assertEquals("Database Systems", secondCourse.getTitle());
        assertEquals("Database design and management principles", secondCourse.getDescription());
=======
>>>>>>> aa25a8ba9dffd68683ff7b8108aa78fda4051845

        assertEquals("Spring Boot Introduction", secondCourse.getTitle());
        assertEquals("Spring Boot framework usage training", secondCourse.getDescription());
>>>>>>> 61e1b47dcabb2e89fc5cf8a83377ae21cb3c2843
        assertNotNull(secondCourse.getId());
        assertNotNull(secondCourse.getDate());
=======
        assertEquals("Spring Boot Introduction", secondCourse.getTitle());
        assertEquals("Spring Boot Framework Training", secondCourse.getDescription());
        assertNotNull(secondCourse.getId());
        assertNotNull(secondCourse.getDate());
        
        // 驗證課程列表的順序
        assertTrue(firstCourse.getDate().compareTo(secondCourse.getDate()) <= 0,
                  "課程應該按照日期排序");
>>>>>>> ch
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
<<<<<<< HEAD
        assertEquals("Development", department.getName());
        
        // 驗證課程列表
        assertNotNull(department.getCourses());
        assertEquals(2, department.getCourses().size(), "Development Department 應該有2個課程");
        
        // 驗證第一個課程
        DepartmentResponse.CourseData firstCourse = department.getCourses().get(0);
        assertEquals("Java Basics", firstCourse.getTitle());
        assertEquals("Basic Java Programming Training", firstCourse.getDescription());
=======
        assertEquals("Computer Science Department", department.getName());
        
        // 驗證課程列表
        assertNotNull(department.getCourses());
        assertEquals(4, department.getCourses().size(), "Computer Science Department 應該有4個課程");
        
        // 驗證第一個課程
        DepartmentResponse.CourseData firstCourse = department.getCourses().get(0);
        assertEquals("Data Structures and Algorithms", firstCourse.getTitle());
        assertEquals("Fundamental data structures and algorithm design", firstCourse.getDescription());
>>>>>>> aa25a8ba9dffd68683ff7b8108aa78fda4051845
        assertNotNull(firstCourse.getId());
        assertNotNull(firstCourse.getDate());
        
        // 驗證第二個課程
        DepartmentResponse.CourseData secondCourse = department.getCourses().get(1);
<<<<<<< HEAD
<<<<<<< HEAD
        assertEquals("Python Basic Training", secondCourse.getTitle());
        assertEquals("Python programming basic knowledge training", secondCourse.getDescription());
=======
        assertEquals("Spring Boot Introduction", secondCourse.getTitle());
        assertEquals("Spring Boot Framework Training", secondCourse.getDescription());
>>>>>>> ch
=======
        assertEquals("Database Systems", secondCourse.getTitle());
        assertEquals("Database design and management principles", secondCourse.getDescription());
=======
        assertEquals("Python Basic Training", secondCourse.getTitle());
        assertEquals("Python programming basic knowledge training", secondCourse.getDescription());
>>>>>>> 61e1b47dcabb2e89fc5cf8a83377ae21cb3c2843
>>>>>>> aa25a8ba9dffd68683ff7b8108aa78fda4051845
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