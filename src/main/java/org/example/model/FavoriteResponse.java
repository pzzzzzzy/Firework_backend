package org.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {
    private int code;
    private String message;
    private Object data;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavoriteData {
        private String id;
        private String userId;
        private String name;
        private String description;
        private Boolean isPublic;
        private Integer resourceCount;
        private String createdAt;
        private String updatedAt;
        private List<ResourceData> resources;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResourceData {
        private String id;
        private String resourceId;
        private String title;
        private String fileType;
        private Long size;
        private String uploadTime;
        private String removedAt;
    }
} 