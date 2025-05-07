package com.royal.iam_service.application.service;

import com.evo.common.UserAuthority;
import com.royal.iam_service.application.dto.request.SearchUserRequest;
import com.royal.iam_service.application.dto.response.UserDTO;

import java.util.List;

public interface UserQueryService {
    UserDTO getUserInfo(String username);

    Long totalUsers(SearchUserRequest request);

    List<UserDTO> search(SearchUserRequest searchUserRequest);

    byte[] exportUserListToExcel(SearchUserRequest searchUserRequest);

    UserAuthority getUserAuthority(String username);
//    UserAuthority getClientAuthority(String clientId);
}
