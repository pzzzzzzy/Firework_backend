package org.example.repository;

import org.example.entity.StudyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyResourceRepository extends JpaRepository<StudyResource, Integer> {
    // 根据课程ID查找所有学习资源
    List<StudyResource> findByCourseId(Long courseId);

    // 根据文件类型查找学习资源
    List<StudyResource> findByFileType(String fileType);

    // 根据课程ID和文件类型查找学习资源
    List<StudyResource> findByCourseIdAndFileType(Long courseId, String fileType);
    
    // 根据文件名模糊搜索
    List<StudyResource> findByNameContainingIgnoreCase(String keyword);
} 