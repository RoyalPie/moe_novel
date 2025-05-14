package com.royal.novel_service.infrastructure.persistence.repository;

import com.royal.novel_service.infrastructure.persistence.entity.NovelGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NovelGenreEntityRepository extends JpaRepository<NovelGenreEntity, UUID> {
    List<NovelGenreEntity> findByNovelIdIn(List<UUID> novelIds);
}
