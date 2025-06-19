package com.royal.novel_service.infrastructure.persistence.entity;

import com.evo.common.validator.ValidateConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "novel_tags")
public class NovelTagEntity {
    @Id
    @Column(name = "id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID id;

    @Column(name = "novel_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID novelId;

    @Column(name = "tag_id", length = ValidateConstraint.LENGTH.ID_MAX_LENGTH)
    private UUID tagId;

    @Column(name = "deleted")
    private boolean deleted;
}
