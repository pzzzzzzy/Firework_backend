package org.example.repository;

import org.example.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    // 你可以在这里定义一些自定义查询方法（如果有需要）
}
