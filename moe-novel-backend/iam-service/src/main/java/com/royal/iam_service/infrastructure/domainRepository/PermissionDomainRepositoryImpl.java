package com.royal.iam_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.domain.query.SearchPermissionQuery;
import com.royal.iam_service.domain.repository.PermissionDomainRepository;
import com.royal.iam_service.infrastructure.persistence.entity.PermissionEntity;
import com.royal.iam_service.infrastructure.persistence.mapper.PermissionEntityMapper;
import com.royal.iam_service.infrastructure.persistence.repository.PermissionEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PermissionDomainRepositoryImpl extends AbstractDomainRepository<Permission, PermissionEntity, UUID>
        implements PermissionDomainRepository {
    private final PermissionEntityMapper entityMapper;
    private final PermissionEntityRepository repository;

    public PermissionDomainRepositoryImpl(PermissionEntityRepository repository, PermissionEntityMapper entityMapper) {
        super(repository, entityMapper);
        this.repository = repository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Permission> findPermissionByRoleId(UUID roleId) {
        List<PermissionEntity> permissionEntities = repository.findPermissionByRoleId(roleId);
        return entityMapper.toDomainModelList(permissionEntities);
    }

    @Override
    public List<Permission> search(SearchPermissionQuery searchPermissionQuery) {
        List<PermissionEntity> permissionEntities = repository.search(searchPermissionQuery);
        return permissionEntities.stream().map(entityMapper::toDomainModel).toList();
    }

    @Override
    public Long count(SearchPermissionQuery searchPermissionQuery) {
        return repository.count(searchPermissionQuery);
    }

    @Override
    public Optional<Permission> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Permission getById(UUID uuid) {
        return entityMapper.toDomainModel(repository.findById(uuid).orElseThrow(() -> new RuntimeException("Permission not found")));
    }
}