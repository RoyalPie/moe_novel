package com.royal.iam_service.application.service.impl.query;

import com.evo.common.UserAuthority;
import com.evo.common.webapp.security.AuthorityService;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.domain.Role;
import com.royal.iam_service.domain.User;
import com.royal.iam_service.domain.UserRole;
import com.royal.iam_service.domain.repository.RoleDomainRepository;
import com.royal.iam_service.domain.repository.UserDomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class AuthorityQueryServiceImpl implements AuthorityService {

    private final RoleDomainRepository roleDomainRepository;
    private final UserDomainRepository userDomainRepository;

    @Override
    public UserAuthority getUserAuthority(UUID userId) {
        return null;
    }

    @Override
    public UserAuthority getUserAuthority(String username) {
        User user = userDomainRepository.getByUsername(username);
        List<UserRole> userRoles = user.getUserRole();
        List<Role> roles = userRoles.stream()
                .map(userRole -> roleDomainRepository.getById(userRole.getRoleId()))
                .toList();
        boolean isRoot = roles.stream().anyMatch(Role::isRoot);
        List<Permission> allPermissions = roles.stream()
                .flatMap(role -> roleDomainRepository.findPermissionByRoleId(role.getRoleId()).stream())
                .toList();
        List<String> grantedPermissions = allPermissions.stream()
                .filter(Objects::nonNull)
                .map(permission -> permission.getResource() + "." + permission.getScope())
                .toList();

        return UserAuthority.builder()
                .userId(user.getUserId())
                .isRoot(isRoot)
                .grantedPermissions(grantedPermissions)
                .build();
    }

    @Override
    public UserAuthority getClientAuthority(UUID clientId) {
        return null;
    }
}
