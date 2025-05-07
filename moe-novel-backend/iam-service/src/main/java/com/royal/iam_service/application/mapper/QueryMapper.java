package com.royal.iam_service.application.mapper;

import com.royal.iam_service.application.dto.request.SearchPermissionRequest;
import com.royal.iam_service.application.dto.request.SearchUserRequest;
import com.royal.iam_service.domain.query.SearchPermissionQuery;
import com.royal.iam_service.domain.query.SearchUserQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    SearchPermissionQuery from(SearchPermissionRequest request);
    SearchUserQuery from(SearchUserRequest request);
}
