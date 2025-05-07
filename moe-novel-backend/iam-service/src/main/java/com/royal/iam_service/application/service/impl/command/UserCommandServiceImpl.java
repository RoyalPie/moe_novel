package com.royal.iam_service.application.service.impl.command;

import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.dto.request.SyncUserRequest;
import com.evo.common.dto.response.FileResponse;
import com.royal.iam_service.application.dto.mapper.UserDTOMapper;
import com.royal.iam_service.application.dto.request.ChangePasswordRequest;
import com.royal.iam_service.application.dto.request.CreateUserRequest;
import com.royal.iam_service.application.dto.request.UpdateUserRequest;
import com.royal.iam_service.application.dto.response.UserDTO;
import com.royal.iam_service.application.mapper.CommandMapper;
import com.royal.iam_service.application.mapper.SyncMapper;
import com.royal.iam_service.application.service.UserCommandService;
import com.royal.iam_service.domain.Role;
import com.royal.iam_service.domain.User;
import com.royal.iam_service.domain.UserActivityLog;
import com.royal.iam_service.domain.UserRole;
import com.royal.iam_service.domain.command.ChangePasswordCmd;
import com.royal.iam_service.domain.command.CreateUserCmd;
import com.royal.iam_service.domain.command.CreateUserRoleCmd;
import com.royal.iam_service.domain.command.LockUserCmd;
import com.royal.iam_service.domain.command.ResetKeycloakPasswordCmd;
import com.royal.iam_service.domain.command.UpdateUserCmd;
import com.royal.iam_service.domain.command.WriteLogCmd;
import com.royal.iam_service.domain.repository.RoleDomainRepository;
import com.royal.iam_service.domain.repository.UserDomainRepository;
import com.royal.iam_service.infrastructure.adapter.keycloak.KeycloakCommandClient;
import com.royal.iam_service.infrastructure.adapter.keycloak.KeycloakIdentityClient;
import com.royal.iam_service.infrastructure.adapter.keycloak.KeycloakQueryClient;
import com.royal.iam_service.infrastructure.adapter.mail.EmailService;
import com.royal.iam_service.infrastructure.adapter.storage.FileService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {
    private final KeycloakCommandClient keycloakCommandClient;
    private final CommandMapper commandMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDomainRepository userDomainRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleDomainRepository roleDomainRepository;
    private final KeycloakQueryClient keycloakQueryClient;
    private final KeycloakIdentityClient keycloakIdentityClient;
    private final EmailService emailService;
    private final FileService fileService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final SyncMapper syncMapper;

    @Value("${keycloak.enabled}")
    boolean keycloakEnabled;

    @Override
    public UserDTO createDefaultUser(CreateUserRequest request) {
        try {
            CreateUserCmd createUserCmd = commandMapper.from(request);
            createUserCmd.setPassword(passwordEncoder.encode(createUserCmd.getPassword()));

            createUserCmd.setKeycloakUserId(UUID.fromString(keycloakCommandClient.createKeycloakUser(request)));
            Role role = roleDomainRepository.findByName("ROLE_USER");
            User user = new User(createUserCmd);
            CreateUserRoleCmd createUserRoleCmd = new CreateUserRoleCmd(role.getRoleId(), user.getUserId());
            WriteLogCmd logCmd = new WriteLogCmd();
            logCmd.setActivity("SignUp");
            UserActivityLog log = new UserActivityLog(logCmd);
            List<UserRole> userRoles = new ArrayList<>();
            userRoles.add(new UserRole(createUserRoleCmd));
            user.setUserRole(userRoles);
            user.setUserActivityLog(log);
            user.setAvatarFileId(UUID.fromString("e4fd0fd5-e4e8-471c-95fc-41f8466bb224"));
            user = userDomainRepository.save(user);
            SyncUserRequest syncUserRequest = syncMapper.from(user);
            SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                    .syncAction("CREATE_USER")
                    .syncUserRequest(syncUserRequest)
                    .build();
            kafkaTemplate.send("sync-user", syncUserEvent);
            return userDTOMapper.domainModelToDTO(user);
        } catch (FeignException e) {
            throw new RuntimeException("Cant create user");
        }
    }

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        UUID keycloakUserId = UUID.fromString(keycloakCommandClient.createKeycloakUser(request));

        CreateUserCmd createUserCmd = commandMapper.from(request);
        createUserCmd.setPassword(passwordEncoder.encode(createUserCmd.getPassword()));

        createUserCmd.setKeycloakUserId(keycloakUserId);
        User user = new User(createUserCmd);
        userDomainRepository.save(user);
        return userDTOMapper.domainModelToDTO(user);
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) {
        ChangePasswordCmd changePasswordCmd = commandMapper.from(request);

        User user = userDomainRepository.getByUsername(username);
        UUID keycloakUserId = user.getKeycloakUserId();

        String token = keycloakQueryClient.getClientToken();
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            ResetKeycloakPasswordCmd resetKeycloakPasswordCmd = ResetKeycloakPasswordCmd.builder().value(changePasswordCmd.getNewPassword()).build();
            keycloakCommandClient.resetPassword("Bearer " + token, keycloakUserId, resetKeycloakPasswordCmd);

            user.changePassword(passwordEncoder.encode(request.getNewPassword()));
            userDomainRepository.save(user);

            emailService.sendMailAlert(user.getEmail(), "change_password");
        } else {
            throw new RuntimeException("Auth Error");
        }
    }

    @Override
    public String assignRole(String username, UpdateUserRequest request) {
        UpdateUserCmd cmd = commandMapper.from(request);
        User user = userDomainRepository.getByUsername(username);
        user.update(cmd);
        return "";
    }

    @Override
    public UUID changeAvatar(String username, List<MultipartFile> files) {
        User user = userDomainRepository.getByUsername(username);
        FileResponse fileResponse = fileService.uploadFile(files).getFirst();
        UUID avatarId = fileResponse.getId();
        user.changeAvatar(avatarId);
        WriteLogCmd logCmd = new WriteLogCmd();
        logCmd.setActivity("CHANGE_AVATAR");
        UserActivityLog log = new UserActivityLog(logCmd);
        user.setUserActivityLog(log);
        userDomainRepository.save(user);
        SyncUserRequest syncUserRequest = syncMapper.from(user);
        SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                .syncAction("UPDATE_USER")
                .syncUserRequest(syncUserRequest)
                .build();
        kafkaTemplate.send("sync-user", syncUserEvent);
        return avatarId;
    }

    @Override
    public List<UserDTO> importUserFile(MultipartFile file) {
        try {
            List<UserDTO> userDTOS = new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            rowIterator.next();

            while (rowIterator.hasNext()) {
                List<String> errors = new ArrayList<>();
                Row row = rowIterator.next();

                CreateUserRequest.CreateUserRequestBuilder createUserRequestBuilder = CreateUserRequest.builder();

                int rowIndex = row.getRowNum() + 1;
                Cell usernameCell = row.getCell(1);
                Cell passwordCell = row.getCell(2);
                Cell emailNameCell = row.getCell(3);
                Cell firstNameCell = row.getCell(4);
                Cell lastNameCell = row.getCell(5);
                Cell dobCell = row.getCell(6);
                Cell streetCell = row.getCell(7);
                Cell wardCell = row.getCell(8);
                Cell districtCell = row.getCell(9);
                Cell cityCell = row.getCell(10);
                Cell phoneNumberCell = row.getCell(11);

                if (usernameCell != null) {
                    String username = usernameCell.getStringCellValue().trim();
                    if (username.isEmpty()) {
                        errors.add("Dòng " + rowIndex + ": Username bị trống.");
                    } else if (userDomainRepository.existsByUsername(username)) {
                        errors.add("Dòng " + rowIndex + ": Username '" + username + "' đã tồn tại.");
                    } else {
                        createUserRequestBuilder.username(username);
                    }
                } else {
                    errors.add("Dòng " + rowIndex + ": Username bị trống.");
                }

                if (passwordCell != null) {
                    String password = passwordCell.getStringCellValue().trim();
                    if (password.isEmpty()) {
                        errors.add("Dòng " + rowIndex + ": Password bị trống.");
                    } else {
                        createUserRequestBuilder.password(passwordEncoder.encode(password));
                    }
                } else {
                    errors.add("Dòng " + rowIndex + ": Password bị trống.");
                }

                if (emailNameCell != null) {
                    String email = emailNameCell.getStringCellValue().trim();
                    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        errors.add("Dòng " + rowIndex + ": Email không hợp lệ.");
                    } else {
                        createUserRequestBuilder.email(email);
                    }
                }

                if (firstNameCell != null && lastNameCell != null) {
                    String firstName = firstNameCell.getStringCellValue().trim();
                    String lastName = lastNameCell.getStringCellValue().trim();
                    if (firstName.isEmpty() || lastName.isEmpty()) {
                        errors.add("Dòng " + rowIndex + ": Họ hoặc Tên bị trống.");
                    } else {
                        createUserRequestBuilder.firstName(firstName);
                        createUserRequestBuilder.lastName(lastName);
                    }
                } else {
                    errors.add("Dòng " + rowIndex + ": Họ hoặc Tên bị trống.");
                }

                if (dobCell != null) {
                    try {
                        createUserRequestBuilder.dateOfBirth(dobCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    } catch (Exception e) {
                        errors.add("Dòng " + rowIndex + ": Ngày sinh không hợp lệ.");
                    }
                } else {
                    errors.add("Dòng " + rowIndex + ": Ngày sinh bị trống.");
                }

                if (phoneNumberCell != null) {
                    String phoneNumber = phoneNumberCell.getStringCellValue();
                    if (phoneNumber.matches("^0\\d{8,10}$")) {  // Bắt đầu bằng 0, 9-11 số
                        createUserRequestBuilder.phoneNumber(phoneNumber);
                    } else {
                        errors.add("Dòng " + rowIndex + ": Số điện thoại không hợp lệ. Phải bắt đầu bằng 0 và có từ 9 đến 11 chữ số.");
                    }
                } else {
                    errors.add("Dòng " + rowIndex + ": Số điện thoại bị trống.");
                }

                if (streetCell != null) createUserRequestBuilder.street(streetCell.getStringCellValue().trim());
                if (wardCell != null) createUserRequestBuilder.ward(wardCell.getStringCellValue().trim());
                if (districtCell != null) createUserRequestBuilder.district(districtCell.getStringCellValue().trim());
                if (cityCell != null) createUserRequestBuilder.city(cityCell.getStringCellValue().trim());

                if (!errors.isEmpty()) {
                    for (String error : errors) {
                        log.warn(error);
                    }
                } else {
                    CreateUserRequest request = createUserRequestBuilder.build();
                    userDTOS.add(createDefaultUser(request));
                }
            }
            return userDTOS;
        } catch (IOException e) {
            throw new RuntimeException("Auth Error");
        }
    }

    @Override
    public UserDTO updateUser(String username, UpdateUserRequest updateUserRequest) {
        UpdateUserCmd cmd = commandMapper.from(updateUserRequest);
        User user = userDomainRepository.getByUsername(username);
        user.update(cmd);
        SyncUserRequest syncUserRequest = syncMapper.from(user);
        SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                .syncAction("UPDATE_USER")
                .syncUserRequest(syncUserRequest)
                .build();
        kafkaTemplate.send("sync-user", syncUserEvent);
        return userDTOMapper.domainModelToDTO(userDomainRepository.save(user));
    }

    @Override
    public void lockUser(String username, boolean enabled) {
        User user = userDomainRepository.getByUsername(username);
        user.setActive(!enabled);
        userDomainRepository.save(user);
        SyncUserRequest syncUserRequest = syncMapper.from(user);
        SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                .syncAction("UPDATE_USER")
                .syncUserRequest(syncUserRequest)
                .build();
        kafkaTemplate.send("sync-user", syncUserEvent);
        String token = keycloakQueryClient.getClientToken();
        keycloakIdentityClient.lockUser("Bearer " + token, user.getKeycloakUserId().toString(), LockUserCmd.builder().enabled(enabled).build());
    }

}
