package org.example.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LoginResponse {
    private Integer code;
    private String message;
    private Data data;

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    @lombok.Data
    @Builder
    public static class Data {
        private String token;
        private UserInfo userInfo;

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public UserInfo getUserInfo() { return userInfo; }
        public void setUserInfo(UserInfo userInfo) { this.userInfo = userInfo; }
    }

    @lombok.Data
    @Builder
    public static class UserInfo {
        private String phone;
        private String username;
        private String role;
        private String avatar;

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public String getAvatar() { return avatar; }
        public void setAvatar(String avatar) { this.avatar = avatar; }
    }
} 