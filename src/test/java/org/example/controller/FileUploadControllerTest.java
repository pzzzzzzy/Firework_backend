package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Course;
import org.example.repository.CourseRepository;
import org.example.repository.StudyResourceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${file.upload.dir}")
    private String uploadDir;

    @BeforeEach
    void setup() {
        // 确保上传目录存在
        new File(uploadDir).mkdirs();
    }

    @AfterEach
    void cleanup() {
        // 清理测试文件
        File dir = new File(uploadDir);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    @Test
    public void testUploadChunk() throws Exception {
        // 创建测试文件内容
        String content = "This is a test file content";
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "test.txt",
            MediaType.TEXT_PLAIN_VALUE,
            content.getBytes()
        );

        // 执行文件上传请求
        MvcResult result = mockMvc.perform(multipart("/api/files/chunk")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> response = objectMapper.readValue(responseBody, Map.class);
        
        assertEquals(200, response.get("code"));
        assertEquals("文件上传成功", response.get("message"));
        assertNotNull(response.get("data"));
        
        // 验证上传的文件信息
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        assertNotNull(data.get("fileName"));
        assertEquals("test.txt", data.get("originalFileName"));
        assertEquals(content.getBytes().length, data.get("fileSize"));
        assertEquals(MediaType.TEXT_PLAIN_VALUE, data.get("fileType"));
        
        // 验证文件是否实际保存到了磁盘
        String savedFileName = (String) data.get("fileName");
        File savedFile = new File(uploadDir, savedFileName);
        assertTrue(savedFile.exists());
        assertEquals(content, new String(Files.readAllBytes(savedFile.toPath())));
    }

    @Test
    public void testMergeAndSave() throws Exception {
        // 准备测试数据
        Map<String, Object> mergeRequest = new HashMap<>();
        mergeRequest.put("fileName", "merged_test.txt");
        mergeRequest.put("fileSize", 1024L);
        mergeRequest.put("title", "Test Document");
        mergeRequest.put("type", "text/plain");
        mergeRequest.put("category", "document");
        mergeRequest.put("courseId", 1L);
        mergeRequest.put("courseName", "Test Course");

        // 执行合并请求
        MvcResult result = mockMvc.perform(post("/api/files/merge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mergeRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> response = objectMapper.readValue(responseBody, Map.class);
        
        assertEquals(200, response.get("code"));
        assertEquals("文件信息保存成功", response.get("message"));
        
        // 验证保存的文件信息
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        assertNotNull(data.get("fileId"));
        assertEquals("merged_test.txt", data.get("fileName"));
        assertEquals(1024, data.get("fileSize"));
        assertEquals("text/plain", data.get("fileType"));
        assertNotNull(data.get("uploadTime"));
        assertEquals("Test Document", data.get("title"));
        assertEquals("document", data.get("category"));
        assertEquals(1, data.get("courseId"));
        assertEquals("Test Course", data.get("courseName"));
    }

    @Test
    public void testUploadInvalidFile() throws Exception {
        // 创建一个空文件
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "",
            MediaType.TEXT_PLAIN_VALUE,
            new byte[0]
        );

        // 执行文件上传请求
        MvcResult result = mockMvc.perform(multipart("/api/files/chunk")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> response = objectMapper.readValue(responseBody, Map.class);
        
        assertEquals(500, response.get("code"));
        assertTrue(((String) response.get("message")).contains("文件上传失败"));
        assertNull(response.get("data"));
    }

    @Test
    public void testMergeWithInvalidCourseId() throws Exception {
        // 准备测试数据，使用不存在的课程ID
        Map<String, Object> mergeRequest = new HashMap<>();
        mergeRequest.put("fileName", "test.txt");
        mergeRequest.put("fileSize", 1024L);
        mergeRequest.put("title", "Test Document");
        mergeRequest.put("type", "text/plain");
        mergeRequest.put("category", "document");
        mergeRequest.put("courseId", 999L); // 使用不存在的课程ID
        mergeRequest.put("courseName", "Non-existent Course");

        // 执行合并请求
        MvcResult result = mockMvc.perform(post("/api/files/merge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mergeRequest)))
                .andExpect(status().isNotFound())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        Map<String, Object> response = objectMapper.readValue(responseBody, Map.class);
        
        assertEquals(404, response.get("code"));
        assertEquals("Course not found", response.get("message"));
        assertNull(response.get("data"));
    }
} 