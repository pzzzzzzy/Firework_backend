package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterRequestTest {

    @Test
    public void testRegisterRequestCreation() {
        RegisterRequest request = new RegisterRequest();
        assertNotNull(request);
    }

    @Test
    public void testRegisterRequestProperties() {
        RegisterRequest request = new RegisterRequest();
        
        // 設置屬性
        request.setPhone("13888888888");
        request.setUsername("testuser");
        request.setPassword("password123");

        // 驗證屬性
        assertEquals("13888888888", request.getPhone());
        assertEquals("testuser", request.getUsername());
        assertEquals("password123", request.getPassword());
    }

    @Test
    public void testRegisterRequestBuilder() {
        RegisterRequest request = RegisterRequest.builder()
                .phone("13888888888")
                .username("testuser")
                .password("password123")
                .build();

        assertEquals("13888888888", request.getPhone());
        assertEquals("testuser", request.getUsername());
        assertEquals("password123", request.getPassword());
    }

    @Test
    public void testRegisterRequestEquality() {
        RegisterRequest request1 = new RegisterRequest();
        request1.setPhone("13888888888");
        request1.setUsername("testuser");
        request1.setPassword("password123");

        RegisterRequest request2 = new RegisterRequest();
        request2.setPhone("13888888888");
        request2.setUsername("testuser");
        request2.setPassword("password123");

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    public void testRegisterRequestToString() {
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13888888888");
        request.setUsername("testuser");
        request.setPassword("password123");

        String toString = request.toString();
        assertTrue(toString.contains("13888888888"));
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("password123"));
    }
} 