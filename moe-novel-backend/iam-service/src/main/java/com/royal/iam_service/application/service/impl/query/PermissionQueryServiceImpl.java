package com.royal.iam_service.application.service.impl.query;

import com.royal.iam_service.application.dto.mapper.PermissionDTOMapper;
import com.royal.iam_service.application.dto.request.SearchPermissionRequest;
import com.royal.iam_service.application.dto.response.PermissionDTO;
import com.royal.iam_service.application.mapper.QueryMapper;
import com.royal.iam_service.application.service.PermissionQueryService;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.domain.query.SearchPermissionQuery;
import com.royal.iam_service.domain.repository.PermissionDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionQueryServiceImpl implements PermissionQueryService {
    private final PermissionDomainRepository permissionDomainRepository;
    private final QueryMapper queryMapper;
    private final PermissionDTOMapper permissionDTOMapper;

    @Override
    public List<PermissionDTO> search(SearchPermissionRequest searchPermissionRequest) {
        SearchPermissionQuery searchPermissionQuery = queryMapper.from(searchPermissionRequest);
        List<Permission> permissions = permissionDomainRepository.search(searchPermissionQuery);
        return permissions.stream().map(permissionDTOMapper::domainModelToDTO).toList();

    }

    @Override
    public Long totalPermissions(SearchPermissionRequest searchPermissionRequest) {
        SearchPermissionQuery searchPermissionQuery = queryMapper.from(searchPermissionRequest);
        return permissionDomainRepository.count(searchPermissionQuery);
    }
}
