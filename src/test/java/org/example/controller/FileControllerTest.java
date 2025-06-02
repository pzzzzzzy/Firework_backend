package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.App;
import org.example.model.FileListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {App.class, FileControllerTest.TestConfig.class})
@AutoConfigureMockMvc
@Sql(scripts = {"/schema.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class FileControllerTest {

    @Configuration
    @Order(1)
    static class TestConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll();
        }

        @Bean
        @Primary
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetCourseFiles() throws Exception {
        // 测试获取Java课程(ID=1)的文件列表
        MvcResult result = mockMvc.perform(get("/api/files/courses/1/files"))
                .andExpect(status().isOk())
                .andReturn();

        // 解析响应
        String responseBody = result.getResponse().getContentAsString();
        FileListResponse response = objectMapper.readValue(responseBody, FileListResponse.class);

        // 验证文件列表不为空
        assertNotNull(response.getFiles());
        assertEquals(2, response.getFiles().size(), "Java课程应该有2个文件");

        // 验证第一个文件（Java Basic Guide.pdf）
        FileListResponse.FileInfo firstFile = response.getFiles().get(0);
        assertNotNull(firstFile.getId());
        assertEquals("Java Basic Guide.pdf", firstFile.getName());
        assertEquals(1024576L, firstFile.getSize());
        assertEquals("application/pdf", firstFile.getType());
        assertNotNull(firstFile.getUploadTime());

        // 验证第二个文件（Java Examples.zip）
        FileListResponse.FileInfo secondFile = response.getFiles().get(1);
        assertNotNull(secondFile.getId());
        assertEquals("Java Examples.zip", secondFile.getName());
        assertEquals(2048576L, secondFile.getSize());
        assertEquals("application/zip", secondFile.getType());
        assertNotNull(secondFile.getUploadTime());
    }

    @Test
    public void testGetCourseFilesForNonExistentCourse() throws Exception {
        // 测试获取不存在的课程(ID=999)的文件列表
        mockMvc.perform(get("/api/files/courses/999/files"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCourseFilesForEmptyCourse() throws Exception {
        // 测试获取没有文件的课程(ID=5)的文件列表
        MvcResult result = mockMvc.perform(get("/api/files/courses/5/files"))
                .andExpect(status().isOk())
                .andReturn();

        // 解析响应
        String responseBody = result.getResponse().getContentAsString();
        FileListResponse response = objectMapper.readValue(responseBody, FileListResponse.class);

        // 验证文件列表为空
        assertNotNull(response.getFiles());
        assertTrue(response.getFiles().isEmpty(), "招聘技能课程不应该有任何文件");
    }

    @Test
    public void testSearchFiles() throws Exception {
        // 测试搜索Java相关文件
        MvcResult result = mockMvc.perform(get("/api/files/search")
                .param("keyword", "Java"))
                .andExpect(status().isOk())
                .andReturn();

        // 解析响应
        String responseBody = result.getResponse().getContentAsString();
        FileListResponse response = objectMapper.readValue(responseBody, FileListResponse.class);

        // 验证文件列表不为空
        assertNotNull(response.getFiles());
        assertEquals(2, response.getFiles().size(), "应该找到2个Java相关文件");

        // 验证找到的文件
        FileListResponse.FileInfo firstFile = response.getFiles().get(0);
        assertEquals("Java Basic Guide.pdf", firstFile.getName());
        assertEquals("application/pdf", firstFile.getType());
        assertEquals(1024576L, firstFile.getSize());

        FileListResponse.FileInfo secondFile = response.getFiles().get(1);
        assertEquals("Java Examples.zip", secondFile.getName());
        assertEquals("application/zip", secondFile.getType());
        assertEquals(2048576L, secondFile.getSize());
    }

    @Test
    public void testSearchFilesWithNoResults() throws Exception {
        // 测试搜索不存在的文件
        MvcResult result = mockMvc.perform(get("/api/files/search")
                .param("keyword", "NonExistentFile"))
                .andExpect(status().isOk())
                .andReturn();

        // 解析响应
        String responseBody = result.getResponse().getContentAsString();
        FileListResponse response = objectMapper.readValue(responseBody, FileListResponse.class);

        // 验证文件列表为空
        assertNotNull(response.getFiles());
        assertTrue(response.getFiles().isEmpty(), "搜索结果应该为空");
    }

    @Test
    public void testSearchFilesWithEmptyKeyword() throws Exception {
        // 测试空关键字搜索
        mockMvc.perform(get("/api/files/search")
                .param("keyword", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSearchFilesByFileType() throws Exception {
        // 测试按文件类型搜索（PDF文件）
        MvcResult result = mockMvc.perform(get("/api/files/search")
                .param("keyword", "pdf"))
                .andExpect(status().isOk())
                .andReturn();

        // 解析响应
        String responseBody = result.getResponse().getContentAsString();
        FileListResponse response = objectMapper.readValue(responseBody, FileListResponse.class);

        // 验证文件列表
        assertNotNull(response.getFiles());
        assertTrue(response.getFiles().size() >= 3, "应该至少找到3个PDF文件");

        // 验证所有找到的文件都是PDF类型
        for (FileListResponse.FileInfo file : response.getFiles()) {
            assertEquals("application/pdf", file.getType(), "所有文件都应该是PDF类型");
        }
    }
} 