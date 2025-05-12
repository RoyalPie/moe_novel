package com.royal.novel_service.infrastructure.persistence.repository;

import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import com.royal.novel_service.infrastructure.persistence.repository.custom.NovelEntityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NovelEntityRepository extends JpaRepository<NovelEntity, UUID>, NovelEntityRepositoryCustom {
    @Query("from NovelEntity e where e.deleted = false and lower(e.title) = lower(:novelName)")
    Optional<NovelEntity> findByUsername(@Param("novelName") String novelName);
    boolean existsByTitle(String novelTitle);

    @Query("select title from NovelEntity e where e.deleted = false")
    List<String> getAllName();
}
