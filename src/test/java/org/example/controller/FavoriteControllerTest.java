package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Favorite;
import org.example.entity.FavoriteResource;
import org.example.model.FavoriteResourceRequest;
import org.example.model.FavoriteResponse;
import org.example.model.FavoriteUpdateRequest;
import org.example.repository.FavoriteRepository;
import org.example.repository.FavoriteResourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteResourceRepository favoriteResourceRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testGetUserFavorites() throws Exception {
        // Execute get user favorites request
        MvcResult result = mockMvc.perform(get("/api/user/favorite")
                .header("X-USER-ID", "1"))
                .andExpect(status().isOk())
                .andReturn();

        // Verify response
        String responseBody = result.getResponse().getContentAsString();
        FavoriteResponse response = objectMapper.readValue(responseBody, FavoriteResponse.class);
        
        // Verify response status
        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        
        // Verify data
        assertNotNull(response.getData());
        FavoriteResponse.FavoriteData favoriteData = objectMapper.convertValue(response.getData(), FavoriteResponse.FavoriteData.class);
        
        // Verify favorite basic info
        assertEquals("f1", favoriteData.getId());
        assertEquals("1", favoriteData.getUserId());
        assertEquals("My Favorites", favoriteData.getName());
        assertEquals("Personal favorite folder", favoriteData.getDescription());
        assertTrue(favoriteData.getIsPublic());
        assertEquals(2, favoriteData.getResourceCount());
        
        // Verify resources list
        assertNotNull(favoriteData.getResources());
        assertEquals(2, favoriteData.getResources().size());
        
        // Verify first resource
        FavoriteResponse.ResourceData firstResource = favoriteData.getResources().get(0);
        assertEquals("fr1", firstResource.getId());
        assertEquals("Java Study Notes", firstResource.getTitle());
        assertEquals("PDF", firstResource.getFileType());
        assertEquals(1024000L, firstResource.getSize());
        assertNotNull(firstResource.getUploadTime());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testUpdateFavorite() throws Exception {
        // 創建更新請求
        FavoriteUpdateRequest request = new FavoriteUpdateRequest();
        request.setName("Updated Favorite");
        request.setDescription("Updated description");
        request.setIsPublic(true);

        // 執行更新請求
        MvcResult result = mockMvc.perform(put("/api/user/favorite")
                .header("X-USER-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        FavoriteResponse response = objectMapper.readValue(responseBody, FavoriteResponse.class);

        // 驗證響應狀態
        assertEquals(200, response.getCode());
        assertEquals("Update successful", response.getMessage());

        // 驗證更新後的數據
        assertNotNull(response.getData());
        FavoriteResponse.FavoriteData updatedData = objectMapper.convertValue(response.getData(), FavoriteResponse.FavoriteData.class);
        assertEquals("Updated Favorite", updatedData.getName());
        assertEquals("Updated description", updatedData.getDescription());
        assertTrue(updatedData.getIsPublic());
        assertNotNull(updatedData.getUpdatedAt());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testUpdateFavoriteNotFound() throws Exception {
        // 創建更新請求
        FavoriteUpdateRequest request = new FavoriteUpdateRequest();
        request.setName("Updated Favorite");
        request.setDescription("Updated description");
        request.setIsPublic(true);

        // 使用不存在的 userId
        MvcResult result = mockMvc.perform(put("/api/user/favorite")
                .header("X-USER-ID", "999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        FavoriteResponse response = objectMapper.readValue(responseBody, FavoriteResponse.class);

        // 驗證響應狀態
        assertEquals(404, response.getCode());
        assertEquals("Favorite not found", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testAddResourceToFavorite() throws Exception {
        // 創建添加資源請求
        FavoriteResourceRequest request = new FavoriteResourceRequest();
        request.setTitle("New Resource");
        request.setFileType("PDF");
        request.setSize(2048000L);

        // 執行添加資源請求
        MvcResult result = mockMvc.perform(post("/api/user/favorite/resources")
                .header("X-USER-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        FavoriteResponse response = objectMapper.readValue(responseBody, FavoriteResponse.class);

        // 驗證響應狀態
        assertEquals(200, response.getCode());
        assertEquals("Resource added successfully", response.getMessage());

        // 驗證數據
        assertNotNull(response.getData());
        FavoriteResponse.FavoriteData addData = objectMapper.convertValue(response.getData(), FavoriteResponse.FavoriteData.class);
        assertEquals(3, addData.getResourceCount()); // 原本有2個資源，現在應該有3個
        
        // 驗證資源列表
        assertNotNull(addData.getResources());
        assertEquals(3, addData.getResources().size());
        
        // 驗證新添加的資源
        FavoriteResponse.ResourceData newResource = addData.getResources().stream()
            .filter(r -> r.getTitle().equals("New Resource"))
            .findFirst()
            .orElse(null);
        
        assertNotNull(newResource);
        assertEquals("PDF", newResource.getFileType());
        assertEquals(2048000L, newResource.getSize());
        assertNotNull(newResource.getUploadTime());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testAddResourceToFavoriteNotFound() throws Exception {
        // 創建添加資源請求
        FavoriteResourceRequest request = new FavoriteResourceRequest();
        request.setTitle("New Resource");
        request.setFileType("PDF");
        request.setSize(2048000L);

        // 使用不存在的 userId
        MvcResult result = mockMvc.perform(post("/api/user/favorite/resources")
                .header("X-USER-ID", "999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andReturn();

        // 驗證響應
        String responseBody = result.getResponse().getContentAsString();
        FavoriteResponse response = objectMapper.readValue(responseBody, FavoriteResponse.class);

        // 驗證響應狀態
        assertEquals(404, response.getCode());
        assertEquals("Favorite not found", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    public void testRemoveResourceFromFavorite() throws Exception {
        // 先添加一個資源
        FavoriteResourceRequest request = new FavoriteResourceRequest();
        request.setTitle("Test Resource");
        request.setFileType("PDF");
        request.setSize(1024L);

        mockMvc.perform(post("/api/user/favorite/resources")
                .header("X-User-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // 獲取添加的資源ID
        List<Favorite> favorites = favoriteRepository.findByUserId("1");
        assertFalse(favorites.isEmpty());
        Favorite favorite = favorites.get(0);
        List<FavoriteResource> resources = favoriteResourceRepository.findByFavoriteId(favorite.getId());
        assertFalse(resources.isEmpty());
        FavoriteResource resource = resources.get(0);
        String deletedResourceId = resource.getId();

        // 測試移除資源
        mockMvc.perform(delete("/api/user/favorite/resources/{resourceId}", deletedResourceId)
                .header("X-User-Id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Resource removed successfully"))
                .andExpect(jsonPath("$.data.id").value("f1"));

        // 驗證該資源已被移除
        List<FavoriteResource> remainingResources = favoriteResourceRepository.findByFavoriteId(favorite.getId());
        boolean exists = remainingResources.stream().anyMatch(r -> deletedResourceId.equals(r.getId()));
        assertFalse(exists);
    }

    @Test
    public void testRemoveResourceFromFavoriteNotFound() throws Exception {
        mockMvc.perform(delete("/api/user/favorite/resources/non-existent-id")
                .header("X-User-Id", "1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof ResponseStatusException);
                    assertEquals("404 NOT_FOUND \"Resource not found\"", result.getResolvedException().getMessage());
                });
    }
} 