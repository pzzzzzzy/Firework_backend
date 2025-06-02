package org.example.model;

import lombok.Data;
import java.util.List;

@Data
public class FileListResponse {
    private List<FileInfo> files;

    @Data
    public static class FileInfo {
        private String id;
        private String name;
        private Long size;
        private String type;
        private String uploadTime;
    }
} 