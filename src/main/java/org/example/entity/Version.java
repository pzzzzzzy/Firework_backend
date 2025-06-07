package org.example.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "versions")
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private String versionId;

    @Column(name = "file_path", nullable = false)
    private String filePath;
}
