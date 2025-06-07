package org.example.controller;

import org.example.entity.Favorite;
import org.example.entity.FavoriteResource;
import org.example.model.FavoriteResponse;
import org.example.model.FavoriteUpdateRequest;
import org.example.model.FavoriteResourceRequest;
import org.example.repository.FavoriteRepository;
import org.example.repository.FavoriteResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteResourceRepository favoriteResourceRepository;

    @GetMapping("/favorite")
    public ResponseEntity<FavoriteResponse> getUserFavorites(@RequestHeader(value = "X-USER-ID", required = false) String userId) {
        if (userId == null) userId = "1";
        Favorite favorite = favoriteRepository.findByUserId(userId).stream()
            .findFirst()
            .orElse(null);

        if (favorite == null) {
            return ResponseEntity.ok(FavoriteResponse.builder()
                .code(404)
                .message("No favorites found")
                .data(null)
                .build());
        }

        List<FavoriteResource> resources = favoriteResourceRepository.findByFavoriteId(favorite.getId());
        List<FavoriteResponse.ResourceData> resourceDataList = resources.stream()
            .map(resource -> FavoriteResponse.ResourceData.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .fileType(resource.getFileType())
                .size(resource.getSize())
                .uploadTime(resource.getUploadTime().toString())
                .build())
            .collect(Collectors.toList());

        FavoriteResponse.FavoriteData favoriteData = FavoriteResponse.FavoriteData.builder()
            .id(favorite.getId())
            .userId(favorite.getUserId())
            .name(favorite.getName())
            .description(favorite.getDescription())
            .isPublic(favorite.getIsPublic())
            .resourceCount(favorite.getResourceCount())
            .createdAt(favorite.getCreatedAt() == null ? null : favorite.getCreatedAt().toString())
            .updatedAt(favorite.getUpdatedAt() == null ? null : favorite.getUpdatedAt().toString())
            .resources(resourceDataList)
            .build();

        return ResponseEntity.ok(FavoriteResponse.builder()
            .code(200)
            .message("success")
            .data(favoriteData)
            .build());
    }

    @PutMapping("/favorite")
    public ResponseEntity<FavoriteResponse> updateFavorite(@RequestBody FavoriteUpdateRequest request, @RequestHeader(value = "X-USER-ID", required = false) String userId) {
        if (userId == null) userId = "1";
        Favorite favorite = favoriteRepository.findByUserId(userId).stream()
            .findFirst()
            .orElse(null);

        if (favorite == null) {
            return ResponseEntity.status(404).body(FavoriteResponse.builder()
                .code(404)
                .message("Favorite not found")
                .data(null)
                .build());
        }

        // Update favorite information
        favorite.setName(request.getName());
        favorite.setDescription(request.getDescription());
        if (request.getIsPublic() != null) {
            favorite.setIsPublic(request.getIsPublic());
        }
        favorite.setUpdatedAt(LocalDateTime.now());
        
        favorite = favoriteRepository.save(favorite);

        // Get updated resources
        List<FavoriteResource> resources = favoriteResourceRepository.findByFavoriteId(favorite.getId());
        List<FavoriteResponse.ResourceData> resourceDataList = resources.stream()
            .map(resource -> FavoriteResponse.ResourceData.builder()
                .id(resource.getId())
                .title(resource.getTitle())
                .fileType(resource.getFileType())
                .size(resource.getSize())
                .uploadTime(resource.getUploadTime().toString())
                .build())
            .collect(Collectors.toList());

        FavoriteResponse.FavoriteData favoriteData = FavoriteResponse.FavoriteData.builder()
            .id(favorite.getId())
            .userId(favorite.getUserId())
            .name(favorite.getName())
            .description(favorite.getDescription())
            .isPublic(favorite.getIsPublic())
            .resourceCount(favorite.getResourceCount())
            .createdAt(favorite.getCreatedAt() == null ? null : favorite.getCreatedAt().toString())
            .updatedAt(favorite.getUpdatedAt() == null ? null : favorite.getUpdatedAt().toString())
            .resources(resourceDataList)
            .build();

        return ResponseEntity.ok(FavoriteResponse.builder()
            .code(200)
            .message("Update successful")
            .data(favoriteData)
            .build());
    }

    @PostMapping("/favorite/resources")
    public ResponseEntity<FavoriteResponse> addResourceToFavorite(
            @RequestBody FavoriteResourceRequest request,
            @RequestHeader(value = "X-USER-ID", required = false) String userId) {
        if (userId == null) userId = "1";
        
        // Find user's favorite
        Favorite favorite = favoriteRepository.findByUserId(userId).stream()
            .findFirst()
            .orElse(null);

        if (favorite == null) {
            return ResponseEntity.status(404).body(FavoriteResponse.builder()
                .code(404)
                .message("Favorite not found")
                .data(null)
                .build());
        }

        // Create new resource
        FavoriteResource resource = new FavoriteResource();
        resource.setId(UUID.randomUUID().toString());
        resource.setFavoriteId(favorite.getId());
        resource.setTitle(request.getTitle());
        resource.setFileType(request.getFileType());
        resource.setSize(request.getSize());
        resource.setUploadTime(LocalDateTime.now());

        // Save resource
        favoriteResourceRepository.save(resource);

        // Update favorite resource count
        favorite.setResourceCount(favorite.getResourceCount() + 1);
        favorite.setUpdatedAt(LocalDateTime.now());
        favorite = favoriteRepository.save(favorite);

        // Get updated resources
        List<FavoriteResource> resources = favoriteResourceRepository.findByFavoriteId(favorite.getId());
        List<FavoriteResponse.ResourceData> resourceDataList = resources.stream()
            .map(r -> FavoriteResponse.ResourceData.builder()
                .id(r.getId())
                .title(r.getTitle())
                .fileType(r.getFileType())
                .size(r.getSize())
                .uploadTime(r.getUploadTime().toString())
                .build())
            .collect(Collectors.toList());

        FavoriteResponse.FavoriteData favoriteData = FavoriteResponse.FavoriteData.builder()
            .id(favorite.getId())
            .userId(favorite.getUserId())
            .name(favorite.getName())
            .description(favorite.getDescription())
            .isPublic(favorite.getIsPublic())
            .resourceCount(favorite.getResourceCount())
            .createdAt(favorite.getCreatedAt() == null ? null : favorite.getCreatedAt().toString())
            .updatedAt(favorite.getUpdatedAt() == null ? null : favorite.getUpdatedAt().toString())
            .resources(resourceDataList)
            .build();

        return ResponseEntity.ok(FavoriteResponse.builder()
            .code(200)
            .message("Resource added successfully")
            .data(favoriteData)
            .build());
    }

    @DeleteMapping("/favorite/resources/{resourceId}")
    public ResponseEntity<FavoriteResponse> removeResourceFromFavorite(
            @PathVariable String resourceId,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {
        
        if (userId == null) {
            userId = "1"; // 默認用戶ID
        }

        // 查找資源
        FavoriteResource resource = favoriteResourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        // 驗證資源是否屬於該用戶的收藏夾
        Favorite favorite = favoriteRepository.findById(resource.getFavoriteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorite not found"));
        
        if (!favorite.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to remove this resource");
        }

        // 刪除資源
        favoriteResourceRepository.deleteById(resourceId);

        // 更新收藏夾的資源計數
        favorite.setResourceCount(favorite.getResourceCount() - 1);
        favorite = favoriteRepository.save(favorite);

        // 獲取更新後的資源列表
        List<FavoriteResource> resources = favoriteResourceRepository.findByFavoriteId(favorite.getId());
        List<FavoriteResponse.ResourceData> resourceDataList = resources.stream()
            .map(r -> FavoriteResponse.ResourceData.builder()
                .id(r.getId())
                .resourceId(r.getId())
                .title(r.getTitle())
                .fileType(r.getFileType())
                .size(r.getSize())
                .uploadTime(r.getUploadTime().toString())
                .build())
            .collect(Collectors.toList());

        // 構建響應
        FavoriteResponse.FavoriteData favoriteData = FavoriteResponse.FavoriteData.builder()
            .id(favorite.getId())
            .userId(favorite.getUserId())
            .name(favorite.getName())
            .description(favorite.getDescription())
            .isPublic(favorite.getIsPublic())
            .resourceCount(favorite.getResourceCount())
            .createdAt(favorite.getCreatedAt() == null ? null : favorite.getCreatedAt().toString())
            .updatedAt(favorite.getUpdatedAt() == null ? null : favorite.getUpdatedAt().toString())
            .resources(resourceDataList)
            .build();

        return ResponseEntity.ok(FavoriteResponse.builder()
                .code(200)
                .message("Resource removed successfully")
                .data(favoriteData)
                .build());
    }
} 