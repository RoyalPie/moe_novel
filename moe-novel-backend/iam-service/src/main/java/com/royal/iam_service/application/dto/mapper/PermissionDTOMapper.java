package com.royal.iam_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.iam_service.application.dto.response.PermissionDTO;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.infrastructure.persistence.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionDTOMapper extends DTOMapper<PermissionDTO, Permission, PermissionEntity> {

}
