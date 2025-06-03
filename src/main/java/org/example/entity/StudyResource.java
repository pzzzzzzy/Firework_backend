package org.example.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "study_resources")
@Data
public class StudyResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_type", nullable = false, length = 100)
    private String fileType;

    @Column(name = "upload_time", nullable = false)
    private LocalDateTime uploadTime;

    @Column(name = "download_count", nullable = false)
    private int downloadCount = 0;

    @Column(nullable = false)
    private String url;

    // Getter for courseId
    public Long getCourseId() {
        return course != null ? course.getId() : null;
    }

    // Setter for courseId
    public void setCourseId(Long courseId) {
        if (this.course == null) {
            this.course = new Course();
        }
        this.course.setId(courseId);
    }
}
