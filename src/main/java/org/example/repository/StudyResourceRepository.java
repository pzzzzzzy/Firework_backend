package org.example.repository;

import org.example.entity.StudyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyResourceRepository extends JpaRepository<StudyResource, Integer> {
    // 这里不需要额外定义 save 方法，Spring Data JPA 会自动提供
} 