package org.example.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testUserProperties() {
        User user = new User();
        
        // 設置屬性
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("USER");
        user.setPhone("13888888888");

        // 驗證屬性
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("13888888888", user.getPhone());
    }

    @Test
    public void testUserEquality() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("testuser");
        user1.setPassword("password123");
        user1.setRole("USER");
        user1.setPhone("13888888888");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("testuser");
        user2.setPassword("password123");
        user2.setRole("USER");
        user2.setPhone("13888888888");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testUserToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("USER");
        user.setPhone("13888888888");

        String toString = user.toString();
        assertTrue(toString.contains("testuser"));
        assertTrue(toString.contains("USER"));
        assertTrue(toString.contains("13888888888"));
    }
} 