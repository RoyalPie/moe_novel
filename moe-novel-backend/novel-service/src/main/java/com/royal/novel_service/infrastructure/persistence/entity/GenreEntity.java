package com.royal.novel_service.infrastructure.persistence.entity;

import com.evo.common.validator.ValidateConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "genres")
public class GenreEntity {
    @Id
    @Column(columnDefinition = "UUID", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID genreId;

    @NotBlank
    @Column(name = "genre_name", nullable = false)
    private String genreName;

    @Column(name = "deleted")
    private boolean deleted;
}
