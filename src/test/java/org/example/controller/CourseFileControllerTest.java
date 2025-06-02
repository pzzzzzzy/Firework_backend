package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.CourseFileResponse;
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
public class CourseFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetCourseFiles() throws Exception {
        // 测试获取Java Basics课程（ID=85）的文件列表
        MvcResult result = mockMvc.perform(get("/api/files/courses/85/files"))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        CourseFileResponse response = objectMapper.readValue(responseBody, CourseFileResponse.class);

        // 验证基本响应信息
        assertEquals(200, response.getCode());
        assertEquals("Search successful", response.getMessage());
        assertNotNull(response.getData());

        // 验证文件列表不为空且包含3个文件
        assertFalse(response.getData().isEmpty());
        assertEquals(3, response.getData().size());

        // 验证第一个文件（Java Basics Slides）
        CourseFileResponse.CourseFile firstFile = response.getData().get(0);
        assertEquals(85, firstFile.getCourseId());
        assertEquals("Java Basics Slides.pdf", firstFile.getTitle());
        assertEquals("Slides", firstFile.getType());
        assertEquals(1024576, firstFile.getSize());
        assertEquals("2024-03-15", firstFile.getUploadTime());
        assertEquals(10, firstFile.getDownloadCount());
        assertEquals("/files/java-basics-slides.pdf", firstFile.getUrl());

        // 验证第二个文件（Java Exercises）
        CourseFileResponse.CourseFile secondFile = response.getData().get(1);
        assertEquals(85, secondFile.getCourseId());
        assertEquals("Java Exercises.doc", secondFile.getTitle());
        assertEquals("Exercises", secondFile.getType());
        assertEquals(512000, secondFile.getSize());
        assertEquals("2024-03-15", secondFile.getUploadTime());
        assertEquals(5, secondFile.getDownloadCount());
        assertEquals("/files/java-exercises.doc", secondFile.getUrl());

        // 验证第三个文件（Java Examples）
        CourseFileResponse.CourseFile thirdFile = response.getData().get(2);
        assertEquals(85, thirdFile.getCourseId());
        assertEquals("Java Examples.zip", thirdFile.getTitle());
        assertEquals("Examples", thirdFile.getType());
        assertEquals(2048000, thirdFile.getSize());
        assertEquals("2024-03-15", thirdFile.getUploadTime());
        assertEquals(8, thirdFile.getDownloadCount());
        assertEquals("/files/java-examples.zip", thirdFile.getUrl());
    }

    @Test
    public void testGetCourseFilesForNonExistentCourse() throws Exception {
        // 测试获取不存在的课程（ID=999）的文件列表
        mockMvc.perform(get("/api/files/courses/999/files"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCourseFilesForCourseWithNoFiles() throws Exception {
        // 测试获取没有文件的课程（使用一个不存在的课程，ID=91）
        MvcResult result = mockMvc.perform(get("/api/files/courses/91/files"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
} 