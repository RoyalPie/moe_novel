package com.royal.novel_service.infrastructure.persistence.entity;

import com.evo.common.validator.ValidateConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    @Column(columnDefinition = "UUID", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID reviewId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(columnDefinition = "UUID", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID novelId;

    @Column(columnDefinition = "UUID", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID userId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "content")
    private String content;

    @Column(name = "deleted")
    private boolean deleted;
}
