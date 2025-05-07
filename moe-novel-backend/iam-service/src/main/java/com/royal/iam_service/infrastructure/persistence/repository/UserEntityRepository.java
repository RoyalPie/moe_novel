package com.royal.iam_service.infrastructure.persistence.repository;

import com.royal.iam_service.infrastructure.persistence.entity.UserEntity;
import com.royal.iam_service.infrastructure.persistence.repository.custom.UserEntityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID>, UserEntityRepositoryCustom {
    @Query("from UserEntity e where e.active = true and lower(e.username) = lower(:username)")
    Optional<UserEntity> findByUsername(@Param("username") String username);
    boolean existsByUsername(String username);
}
