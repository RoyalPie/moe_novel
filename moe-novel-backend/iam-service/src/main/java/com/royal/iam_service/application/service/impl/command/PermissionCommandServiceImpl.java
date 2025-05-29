package com.royal.iam_service.application.service.impl.command;

import com.royal.iam_service.application.dto.mapper.PermissionDTOMapper;
import com.royal.iam_service.application.dto.request.CreateOrUpdatePermissionRequest;
import com.royal.iam_service.application.dto.response.PermissionDTO;
import com.royal.iam_service.application.mapper.CommandMapper;
import com.royal.iam_service.application.service.PermissionCommandService;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.domain.command.CreateOrUpdatePermissionCmd;
import com.royal.iam_service.domain.repository.PermissionDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionCommandServiceImpl implements PermissionCommandService {
    private final PermissionDomainRepository permissionDomainRepository;
    private final PermissionDTOMapper permissionDTOMapper;
    private final CommandMapper commandMapper;

    @Override
    public PermissionDTO createPermission(CreateOrUpdatePermissionRequest request) {
        CreateOrUpdatePermissionCmd cmd = commandMapper.from(request);
        Permission permission = new Permission(cmd);
        return permissionDTOMapper.domainModelToDTO(permissionDomainRepository.save(permission));
    }

    @Override
    public PermissionDTO updatePermission(CreateOrUpdatePermissionRequest request) {
        CreateOrUpdatePermissionCmd cmd = commandMapper.from(request);
        Permission permission = permissionDomainRepository.getById(cmd.getId());
        permission.update(cmd);
        return permissionDTOMapper.domainModelToDTO(permissionDomainRepository.save(permission));
    }
}
