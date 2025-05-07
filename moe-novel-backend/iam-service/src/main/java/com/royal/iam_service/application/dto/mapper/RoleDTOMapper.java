package com.royal.iam_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.iam_service.application.dto.response.RoleDTO;
import com.royal.iam_service.domain.Role;
import com.royal.iam_service.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleDTOMapper extends DTOMapper<RoleDTO, Role, RoleEntity> {

}
