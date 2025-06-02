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
    Optional<NovelEntity> findByNovelName(@Param("novelName") String novelName);

    boolean existsByTitleAndAuthorName(String novelTitle, String authorName);

    @Query("select title from NovelEntity e where e.deleted = false")
    List<String> getAllName();

    @Query("SELECT COUNT(c) " +
            "FROM NovelEntity n " +
            "LEFT JOIN NovelChapterEntity nc ON n.id = nc.novelId " +
            "LEFT JOIN ChapterEntity c ON nc.chapterId = c.id " +
            "WHERE n.title = :novelTitle")
    int chapterCount(@Param("novelTitle") String novelTitle);
}
