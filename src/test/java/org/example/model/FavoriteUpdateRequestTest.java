package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FavoriteUpdateRequestTest {

    @Test
    public void testFavoriteUpdateRequestCreation() {
        FavoriteUpdateRequest request = new FavoriteUpdateRequest();
        assertNotNull(request);
    }

    @Test
    public void testFavoriteUpdateRequestProperties() {
        FavoriteUpdateRequest request = new FavoriteUpdateRequest();
        request.setName("My Favorites");
        request.setDescription("A collection of my favorite resources");
        request.setIsPublic(true);

        assertEquals("My Favorites", request.getName());
        assertEquals("A collection of my favorite resources", request.getDescription());
        assertTrue(request.getIsPublic());
    }

    @Test
    public void testFavoriteUpdateRequestEquality() {
        FavoriteUpdateRequest request1 = new FavoriteUpdateRequest();
        request1.setName("My Favorites");
        request1.setDescription("A collection of my favorite resources");
        request1.setIsPublic(true);

        FavoriteUpdateRequest request2 = new FavoriteUpdateRequest();
        request2.setName("My Favorites");
        request2.setDescription("A collection of my favorite resources");
        request2.setIsPublic(true);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    public void testFavoriteUpdateRequestToString() {
        FavoriteUpdateRequest request = new FavoriteUpdateRequest();
        request.setName("My Favorites");
        request.setDescription("A collection of my favorite resources");
        request.setIsPublic(true);

        String toString = request.toString();
        assertTrue(toString.contains("My Favorites"));
        assertTrue(toString.contains("A collection of my favorite resources"));
        assertTrue(toString.contains("true"));
    }
} 