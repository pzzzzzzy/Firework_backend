package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Integer code;
    private String message;
    private RegisterData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterData {
        private String phone;
        private String username;
        private String role;
    }
} 