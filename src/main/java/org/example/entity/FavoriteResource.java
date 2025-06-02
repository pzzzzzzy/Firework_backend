package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorite_resources")
public class FavoriteResource {
    @Id
    private String id;
    
    @Column(name = "favorite_id")
    private String favoriteId;
    
    private String title;
    
    @Column(name = "file_type")
    private String fileType;
    
    private Long size;
    
    @Column(name = "upload_time")
    private LocalDateTime uploadTime;
    
    @ManyToOne
    @JoinColumn(name = "favorite_id", insertable = false, updatable = false)
    private Favorite favorite;
} 