package com.royal.iam_service.infrastructure.persistence.repository;

import com.royal.iam_service.infrastructure.persistence.entity.UserActivityLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserActivityLogEntityRepository extends JpaRepository<UserActivityLogEntity, UUID> {
}
