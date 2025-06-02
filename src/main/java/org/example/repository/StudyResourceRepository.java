package org.example.repository;

import org.example.entity.StudyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyResourceRepository extends JpaRepository<StudyResource, Long> {
    List<StudyResource> findByCourseId(Long courseId);

    @Query("SELECT s FROM StudyResource s WHERE s.title LIKE %:keyword%")
    List<StudyResource> findByTitleContaining(@Param("keyword") String keyword);
} 