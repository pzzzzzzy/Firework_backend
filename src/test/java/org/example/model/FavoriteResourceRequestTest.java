package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FavoriteResourceRequestTest {

    @Test
    public void testFavoriteResourceRequestCreation() {
        FavoriteResourceRequest request = new FavoriteResourceRequest();
        assertNotNull(request);
    }

    @Test
    public void testFavoriteResourceRequestAllArgsConstructor() {
        FavoriteResourceRequest request = new FavoriteResourceRequest(
            "Test Resource",
            "PDF",
            1024L
        );

        assertEquals("Test Resource", request.getTitle());
        assertEquals("PDF", request.getFileType());
        assertEquals(1024L, request.getSize());
    }

    @Test
    public void testFavoriteResourceRequestEquality() {
        FavoriteResourceRequest request1 = new FavoriteResourceRequest(
            "Test Resource",
            "PDF",
            1024L
        );

        FavoriteResourceRequest request2 = new FavoriteResourceRequest(
            "Test Resource",
            "PDF",
            1024L
        );

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    public void testFavoriteResourceRequestToString() {
        FavoriteResourceRequest request = new FavoriteResourceRequest(
            "Test Resource",
            "PDF",
            1024L
        );

        String toString = request.toString();
        assertTrue(toString.contains("Test Resource"));
        assertTrue(toString.contains("PDF"));
        assertTrue(toString.contains("1024"));
    }
} 