package com.royal.novel_service.infrastructure.persistence.repository;

import com.royal.novel_service.infrastructure.persistence.entity.NovelTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NovelTagEntityRepository extends JpaRepository<NovelTagEntity, UUID> {
}
