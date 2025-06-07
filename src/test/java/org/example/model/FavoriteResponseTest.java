package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteResponseTest {

    @Test
    public void testFavoriteResponseCreation() {
        FavoriteResponse response = new FavoriteResponse();
        assertNotNull(response);
    }

    @Test
    public void testFavoriteResponseProperties() {
        FavoriteResponse response = new FavoriteResponse();
        response.setCode(200);
        response.setMessage("Success");
        
        FavoriteResponse.FavoriteData favoriteData = new FavoriteResponse.FavoriteData();
        favoriteData.setId("1");
        favoriteData.setUserId("user1");
        favoriteData.setName("My Favorites");
        favoriteData.setDescription("A collection of my favorite resources");
        favoriteData.setIsPublic(true);
        favoriteData.setResourceCount(5);
        favoriteData.setCreatedAt("2024-01-01T00:00:00");
        favoriteData.setUpdatedAt("2024-01-02T00:00:00");
        
        List<FavoriteResponse.ResourceData> resources = new ArrayList<>();
        FavoriteResponse.ResourceData resource = new FavoriteResponse.ResourceData();
        resource.setId("1");
        resource.setResourceId("res1");
        resource.setTitle("Test Resource");
        resource.setFileType("PDF");
        resource.setSize(1024L);
        resource.setUploadTime("2024-01-01T00:00:00");
        resource.setRemovedAt(null);
        resources.add(resource);
        
        favoriteData.setResources(resources);
        response.setData(favoriteData);

        assertEquals(200, response.getCode());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getData());
        
        FavoriteResponse.FavoriteData data = (FavoriteResponse.FavoriteData) response.getData();
        assertEquals("1", data.getId());
        assertEquals("user1", data.getUserId());
        assertEquals("My Favorites", data.getName());
        assertEquals("A collection of my favorite resources", data.getDescription());
        assertTrue(data.getIsPublic());
        assertEquals(5, data.getResourceCount());
        assertEquals("2024-01-01T00:00:00", data.getCreatedAt());
        assertEquals("2024-01-02T00:00:00", data.getUpdatedAt());
        assertEquals(1, data.getResources().size());
        
        FavoriteResponse.ResourceData res = data.getResources().get(0);
        assertEquals("1", res.getId());
        assertEquals("res1", res.getResourceId());
        assertEquals("Test Resource", res.getTitle());
        assertEquals("PDF", res.getFileType());
        assertEquals(1024L, res.getSize());
        assertEquals("2024-01-01T00:00:00", res.getUploadTime());
        assertNull(res.getRemovedAt());
    }

    @Test
    public void testFavoriteResponseBuilder() {
        List<FavoriteResponse.ResourceData> resources = new ArrayList<>();
        FavoriteResponse.ResourceData resource = FavoriteResponse.ResourceData.builder()
            .id("1")
            .resourceId("res1")
            .title("Test Resource")
            .fileType("PDF")
            .size(1024L)
            .uploadTime("2024-01-01T00:00:00")
            .build();
        resources.add(resource);

        FavoriteResponse.FavoriteData favoriteData = FavoriteResponse.FavoriteData.builder()
            .id("1")
            .userId("user1")
            .name("My Favorites")
            .description("A collection of my favorite resources")
            .isPublic(true)
            .resourceCount(5)
            .createdAt("2024-01-01T00:00:00")
            .updatedAt("2024-01-02T00:00:00")
            .resources(resources)
            .build();

        FavoriteResponse response = FavoriteResponse.builder()
            .code(200)
            .message("Success")
            .data(favoriteData)
            .build();

        assertEquals(200, response.getCode());
        assertEquals("Success", response.getMessage());
        assertNotNull(response.getData());
        
        FavoriteResponse.FavoriteData data = (FavoriteResponse.FavoriteData) response.getData();
        assertEquals("1", data.getId());
        assertEquals("user1", data.getUserId());
        assertEquals("My Favorites", data.getName());
        assertEquals("A collection of my favorite resources", data.getDescription());
        assertTrue(data.getIsPublic());
        assertEquals(5, data.getResourceCount());
        assertEquals("2024-01-01T00:00:00", data.getCreatedAt());
        assertEquals("2024-01-02T00:00:00", data.getUpdatedAt());
        assertEquals(1, data.getResources().size());
        
        FavoriteResponse.ResourceData res = data.getResources().get(0);
        assertEquals("1", res.getId());
        assertEquals("res1", res.getResourceId());
        assertEquals("Test Resource", res.getTitle());
        assertEquals("PDF", res.getFileType());
        assertEquals(1024L, res.getSize());
        assertEquals("2024-01-01T00:00:00", res.getUploadTime());
        assertNull(res.getRemovedAt());
    }

    @Test
    public void testFavoriteResponseEquality() {
        FavoriteResponse response1 = FavoriteResponse.builder()
            .code(200)
            .message("Success")
            .data(null)
            .build();

        FavoriteResponse response2 = FavoriteResponse.builder()
            .code(200)
            .message("Success")
            .data(null)
            .build();

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
} 