package com.royal.iam_service.application.service.impl.command;

import com.royal.iam_service.application.dto.mapper.RoleDTOMapper;
import com.royal.iam_service.application.dto.request.CreateOrUpdateRoleRequest;
import com.royal.iam_service.application.dto.response.RoleDTO;
import com.royal.iam_service.application.mapper.CommandMapper;
import com.royal.iam_service.application.service.RoleCommandService;
import com.royal.iam_service.domain.Role;
import com.royal.iam_service.domain.command.CreateOrUpdateRoleCmd;
import com.royal.iam_service.domain.repository.RoleDomainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleDTOMapper roleDTOMapper;
    private final CommandMapper commandMapper;
    private final RoleDomainRepository roleDomainRepository;

    @Override
    @Transactional
    public RoleDTO createRole(CreateOrUpdateRoleRequest createOrUpdateRoleRequest) {
        CreateOrUpdateRoleCmd createOrUpdateRoleCmd = commandMapper.from(createOrUpdateRoleRequest);
        Role role = new Role(createOrUpdateRoleCmd);
        role = roleDomainRepository.save(role);
        return roleDTOMapper.domainModelToDTO(role);
    }

    @Override
    @Transactional
    public RoleDTO updateRole(CreateOrUpdateRoleRequest createOrUpdateRoleRequest) {
        CreateOrUpdateRoleCmd createOrUpdateRoleCmd = commandMapper.from(createOrUpdateRoleRequest);
        Role role = roleDomainRepository.getById(createOrUpdateRoleCmd.getId());
        role.update(createOrUpdateRoleCmd);
        role = roleDomainRepository.save(role);
        return roleDTOMapper.domainModelToDTO(role);
    }
}
