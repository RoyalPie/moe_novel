package com.royal.novel_service.infrastructure.persistence.repository;

import com.royal.novel_service.infrastructure.persistence.entity.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChapterEntityRepository extends JpaRepository<ChapterEntity, UUID> {
    @Query("SELECT c " +
            "FROM NovelEntity n " +
            "LEFT JOIN NovelChapterEntity nc ON n.id = nc.novelId " +
            "LEFT JOIN ChapterEntity c ON nc.chapterId = c.id " +
            "WHERE n.title = :novelTitle and c.chapterNumber = :chapterNumber")
    List<ChapterEntity> findByChapterNumber(@Param("novelTitle") String novelTitle, @Param("chapterNumber") int chapterNumber);
}
