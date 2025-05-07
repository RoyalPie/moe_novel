package com.royal.iam_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.iam_service.application.dto.response.UserDTO;
import com.royal.iam_service.domain.User;
import com.royal.iam_service.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDTOMapper extends DTOMapper<UserDTO, User, UserEntity> {
}
