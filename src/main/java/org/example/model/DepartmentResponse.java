package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    private Integer code;
    private String message;
    private List<DepartmentData> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentData {
        private Long id;
        private String name;
        private List<CourseData> courses;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CourseData {
        private Long id;
        private String title;
        private String description;
        private String date;
    }
} 