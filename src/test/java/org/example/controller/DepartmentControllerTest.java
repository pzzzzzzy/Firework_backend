package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.DepartmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        // 执行获取部门列表请求
        MvcResult result = mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        DepartmentResponse response = objectMapper.readValue(responseBody, DepartmentResponse.class);
        
        // 验证响应状态
        assertEquals(200, response.getCode());
        assertEquals("获取成功", response.getMessage());
        
        // 验证数据
        assertNotNull(response.getData());
        assertEquals(3, response.getData().size()); // 应该有3个部门
        
        // 验证第一个部门的数据
        DepartmentResponse.DepartmentData firstDepartment = response.getData().get(0);
        assertEquals("研发部", firstDepartment.getName());
        assertEquals(2, firstDepartment.getCourses().size()); // 研发部应该有2个课程
        
        // 验证第一个部门的第一个课程
        DepartmentResponse.DepartmentData.CourseData firstCourse = firstDepartment.getCourses().get(0);
        assertEquals("Java基础培训", firstCourse.getTitle());
        assertEquals("Java编程基础知识培训", firstCourse.getDescription());
    }
} 