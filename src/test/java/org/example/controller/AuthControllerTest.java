package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.entity.User;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginSuccess() throws Exception {
        // 准备测试数据
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13800138000"); 
        loginRequest.setPassword("password123");

        // 执行登录请求
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        LoginResponse response = objectMapper.readValue(responseBody, LoginResponse.class);
        
        assertEquals(200, response.getCode());
    }

    @Test
    public void testLoginFailure() throws Exception {
        // 准备错误的测试数据
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13999999999"); // 使用另一个不太可能存在的手机号
        loginRequest.setPassword("wrongpass123");

        // 执行登录请求
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        LoginResponse response = objectMapper.readValue(responseBody, LoginResponse.class);
        
        assertEquals(400, response.getCode());
        assertEquals("Invalid phone or password", response.getMessage());
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        // 准备测试数据
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13777777777"); // 使用一个新的手机号
        registerRequest.setUsername("testuser_new");
        registerRequest.setPassword("password123");

        // 执行注册请求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(200, response.getCode());
        assertEquals("Register success", response.getMessage());
        assertEquals("13777777777", response.getData().getPhone());
        assertEquals("testuser_new", response.getData().getUsername());
    }

    @Test
    public void testRegisterWithInvalidPhone() throws Exception {
        // 准备测试数据（无效的手机号）
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("1234567890"); // 无效的手机号
        registerRequest.setUsername("testuser4");
        registerRequest.setPassword("password123");

        // 执行注册请求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(400, response.getCode());
        assertEquals("Invalid phone format", response.getMessage());
    }

    @Test
    public void testRegisterWithInvalidPassword() throws Exception {
        // 准备测试数据（无效的密码）
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13666666666");
        registerRequest.setUsername("testuser5");
        registerRequest.setPassword("123"); // 无效的密码

        // 执行注册请求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 验证响应
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(400, response.getCode());
        assertEquals("Invalid password format", response.getMessage());
    }

    @Test
    public void testLoginWithEmptyFields() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone(""); // 空號碼
        loginRequest.setPassword(""); // 空密碼

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        LoginResponse response = objectMapper.readValue(responseBody, LoginResponse.class);

        assertEquals(400, response.getCode());
        assertEquals("Invalid phone or password", response.getMessage());
    }
}
