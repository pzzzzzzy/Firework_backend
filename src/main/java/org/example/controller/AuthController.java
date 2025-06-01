package org.example.controller;

import org.example.entity.User;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.RegisterRequest;
import org.example.model.RegisterResponse;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$");

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByPhone(loginRequest.getPhone())
            .filter(user -> loginRequest.getPassword().equals(user.getPassword()))
            .map(user -> {
                String token = UUID.randomUUID().toString();
                LoginResponse response = LoginResponse.builder()
                    .code(200)
                    .message("Login success")
                    .data(LoginResponse.LoginData.builder()
                        .token(token)
                        .userInfo(LoginResponse.UserInfo.builder()
                            .id(user.getId())
                            .phone(user.getPhone())
                            .username(user.getUsername())
                            .role(user.getRole())
                            .build())
                        .build())
                    .build();
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.ok(LoginResponse.builder()
                .code(400)
                .message("Invalid phone or password")
                .data(null)
                .build()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // 驗證手機號格式
        if (!PHONE_PATTERN.matcher(registerRequest.getPhone()).matches()) {
            return ResponseEntity.ok(RegisterResponse.builder()
                .code(400)
                .message("Invalid phone format")
                .data(null)
                .build());
        }

        // 驗證密碼格式
        if (!PASSWORD_PATTERN.matcher(registerRequest.getPassword()).matches()) {
            return ResponseEntity.ok(RegisterResponse.builder()
                .code(400)
                .message("Invalid password format")
                .data(null)
                .build());
        }

        // 檢查手機號是否已被註冊
        if (userRepository.findByPhone(registerRequest.getPhone()).isPresent()) {
            return ResponseEntity.ok(RegisterResponse.builder()
                .code(400)
                .message("Phone already registered")
                .data(null)
                .build());
        }

        // 創建新用戶
        User user = new User();
        user.setPhone(registerRequest.getPhone());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER");

        // 保存用戶
        User savedUser = userRepository.save(user);

        // 返回成功響應
        return ResponseEntity.ok(RegisterResponse.builder()
            .code(200)
            .message("Register success")
            .data(RegisterResponse.RegisterData.builder()
                .phone(savedUser.getPhone())
                .username(savedUser.getUsername())
                .role(savedUser.getRole())
                .build())
            .build());
    }
}