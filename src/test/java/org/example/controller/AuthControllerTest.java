package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;

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
<<<<<<< HEAD
        assertEquals("登录成功", response.getMessage());
=======
        assertEquals("Login success", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
        assertEquals("13800138000", response.getData().getUserInfo().getPhone());
    }

    @Test
    public void testLoginFailure() throws Exception {
        // 准备错误的测试数据
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13900139000");
        loginRequest.setPassword("wrongpass123"); // 修改为符合格式的密码

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
<<<<<<< HEAD
        assertEquals("手机号或密码错误", response.getMessage());
=======
        assertEquals("Invalid phone or password", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
    }

    @Test
    public void testInvalidPhoneFormat() throws Exception {
        // 准备无效的手机号格式
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("1234567890"); // 无效的手机号
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
        
        assertEquals(400, response.getCode());
<<<<<<< HEAD
        assertEquals("手机号或密码错误", response.getMessage());
=======
        assertEquals("Invalid phone or password", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
    }

    @Test
    public void testInvalidPasswordFormat() throws Exception {
        // 准备无效的密码格式
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPhone("13800138000");
        loginRequest.setPassword("123456"); // 只有数字，没有字母

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
<<<<<<< HEAD
        assertEquals("手机号或密码错误", response.getMessage());
=======
        assertEquals("Invalid phone or password", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        // 準備測試數據
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13900139000");
        registerRequest.setUsername("testuser2");
        registerRequest.setPassword("password123");

        // 執行註冊請求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(200, response.getCode());
<<<<<<< HEAD
        assertEquals("註冊成功", response.getMessage());
=======
        assertEquals("Register success", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
        assertEquals("13900139000", response.getData().getPhone());
        assertEquals("testuser2", response.getData().getUsername());
        assertEquals("USER", response.getData().getRole());
    }

    @Test
    public void testRegisterWithExistingPhone() throws Exception {
        // 準備測試數據（使用已存在的手機號）
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13800138000"); // 使用已存在的手機號
        registerRequest.setUsername("testuser3");
        registerRequest.setPassword("password123");

        // 執行註冊請求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(400, response.getCode());
<<<<<<< HEAD
        assertEquals("手機號已被註冊", response.getMessage());
=======
        assertEquals("Phone already registered", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
    }

    @Test
    public void testRegisterWithInvalidPhone() throws Exception {
        // 準備測試數據（無效的手機號）
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("1234567890"); // 無效的手機號
        registerRequest.setUsername("testuser4");
        registerRequest.setPassword("password123");

        // 執行註冊請求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(400, response.getCode());
<<<<<<< HEAD
        assertEquals("無效的手機號格式", response.getMessage());
=======
        assertEquals("Invalid phone format", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
    }

    @Test
    public void testRegisterWithInvalidPassword() throws Exception {
        // 準備測試數據（無效的密碼）
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPhone("13900139001");
        registerRequest.setUsername("testuser5");
        registerRequest.setPassword("123"); // 無效的密碼

        // 執行註冊請求
        MvcResult result = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        RegisterResponse response = objectMapper.readValue(responseBody, RegisterResponse.class);
        
        assertEquals(400, response.getCode());
<<<<<<< HEAD
        assertEquals("密碼格式不正確", response.getMessage());
=======
        assertEquals("Invalid password format", response.getMessage());
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
    }
}
