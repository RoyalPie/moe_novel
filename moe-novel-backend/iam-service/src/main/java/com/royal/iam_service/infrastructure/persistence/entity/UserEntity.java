package com.royal.iam_service.infrastructure.persistence.entity;

import com.evo.common.entity.AuditEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class UserEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "keycloak_id")
    private String keycloakUserId;

    @NotBlank
    @Size(max = 25)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @Column(name = "avatar_picture_id")
    private String avatarFileId;

    @Size(max = 15)
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

}