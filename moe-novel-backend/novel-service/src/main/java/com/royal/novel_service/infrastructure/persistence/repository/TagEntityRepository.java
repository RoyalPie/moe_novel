package com.royal.novel_service.infrastructure.persistence.repository;

import com.royal.novel_service.infrastructure.persistence.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagEntityRepository extends JpaRepository<TagEntity, UUID> {
    TagEntity findByTagName(String tagName);

    Boolean existsByTagNameAndDeletedFalse(String tagName);

    @Query("SELECT n FROM TagEntity n WHERE n.tagId =:tagId AND n.deleted=false")
    Optional<TagEntity> findById(@Param("tagId") UUID tagId);

    @Query("SELECT n FROM TagEntity n WHERE n.deleted=false")
    List<TagEntity> findAllDeletedFalse();
}
