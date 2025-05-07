package com.royal.elasticsearch.application.service;

import com.royal.elasticsearch.application.dto.request.SearchUserRequest;
import com.royal.elasticsearch.application.dto.response.SearchUserResponse;

public interface UserQueryService {
    SearchUserResponse searchUser(SearchUserRequest request);
}
