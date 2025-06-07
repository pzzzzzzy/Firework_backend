package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    @Test
    public void testLoginRequestCreation() {
        LoginRequest request = new LoginRequest();
        assertNotNull(request);
    }

    @Test
    public void testLoginRequestProperties() {
        LoginRequest request = new LoginRequest();
        
        // 設置屬性
        request.setPhone("13888888888");
        request.setPassword("password123");

        // 驗證屬性
        assertEquals("13888888888", request.getPhone());
        assertEquals("password123", request.getPassword());
    }

    @Test
    public void testLoginRequestBuilder() {
        LoginRequest request = LoginRequest.builder()
                .phone("13888888888")
                .password("password123")
                .build();

        assertEquals("13888888888", request.getPhone());
        assertEquals("password123", request.getPassword());
    }

    @Test
    public void testLoginRequestEquality() {
        LoginRequest request1 = new LoginRequest();
        request1.setPhone("13888888888");
        request1.setPassword("password123");

        LoginRequest request2 = new LoginRequest();
        request2.setPhone("13888888888");
        request2.setPassword("password123");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    public void testLoginRequestToString() {
        LoginRequest request = new LoginRequest();
        request.setPhone("13888888888");
        request.setPassword("password123");

        String toString = request.toString();
        assertTrue(toString.contains("13888888888"));
        assertTrue(toString.contains("password123"));
    }
} 