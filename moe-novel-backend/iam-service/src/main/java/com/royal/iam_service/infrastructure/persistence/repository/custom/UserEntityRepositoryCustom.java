package com.royal.iam_service.infrastructure.persistence.repository.custom;

import com.royal.iam_service.domain.query.SearchUserQuery;
import com.royal.iam_service.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public interface UserEntityRepositoryCustom {
    List<UserEntity> search(SearchUserQuery searchUserQuery);
    Long count(SearchUserQuery searchUserQuery);
}
