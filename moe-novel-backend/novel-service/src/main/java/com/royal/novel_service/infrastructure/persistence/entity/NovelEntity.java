package com.royal.novel_service.infrastructure.persistence.entity;

import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "novels")
public class NovelEntity {
    @Id
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID novelId;

    @NotBlank
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Size(max = 120)
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_image_id")
    private UUID coverImage;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private NovelStatus status;

    @Column(name = "total_chapters")
    private int totalChapters;

    @Column(name = "total_views")
    private int totalViews;

    @Column(name = "total_follows")
    private int totalFollows;

    @Column(name = "deleted")
    private boolean deleted;

}
