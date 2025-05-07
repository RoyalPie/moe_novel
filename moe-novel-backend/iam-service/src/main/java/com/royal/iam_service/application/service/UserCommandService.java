package com.royal.iam_service.application.service;

import com.royal.iam_service.application.dto.request.ChangePasswordRequest;
import com.royal.iam_service.application.dto.request.CreateUserRequest;
import com.royal.iam_service.application.dto.request.UpdateUserRequest;
import com.royal.iam_service.application.dto.response.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public interface UserCommandService {
    UserDTO createDefaultUser(CreateUserRequest request);

    UserDTO createUser(CreateUserRequest request);

    void changePassword(String username, ChangePasswordRequest request);

    String assignRole(String username, UpdateUserRequest request);

    UUID changeAvatar(String username, List<MultipartFile> files);

    List<UserDTO> importUserFile(MultipartFile file);

    UserDTO updateUser(String username, UpdateUserRequest updateUserRequest);

    void lockUser(String username, boolean enabled);
}
