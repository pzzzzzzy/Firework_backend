package org.example.repository;

import org.example.entity.FavoriteResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FavoriteResourceRepository extends JpaRepository<FavoriteResource, String> {
    List<FavoriteResource> findByFavoriteId(String favoriteId);
} 