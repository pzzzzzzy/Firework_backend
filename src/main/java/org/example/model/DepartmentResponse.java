package org.example.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CourseData {
    private Long id;
    private String title;
    private String description;
    private String date;
} 