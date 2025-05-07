package com.royal.iam_service.infrastructure.persistence.repository;

import com.royal.iam_service.infrastructure.persistence.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, UUID> {
    List<UserRoleEntity> findByUserIdIn(List<UUID> userIds);
}
