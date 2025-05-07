package com.royal.iam_service.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.royal.iam_service.application.dto.request.SearchUserRequest;
import com.royal.iam_service.application.dto.response.UserDTO;
import com.royal.iam_service.application.service.UserCommandService;
import com.royal.iam_service.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/excel")
public class ExcelController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/import/users")
    public ApiResponses<List<UserDTO>> importUserFile(@RequestParam MultipartFile file) {
        List<UserDTO> userResponse = userCommandService.importUserFile(file);
        return ApiResponses.<List<UserDTO>>builder()
                .data(userResponse)
                .success(true)
                .code(200)
                .message("Import user file successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @GetMapping("/export/users")
    public ResponseEntity<byte[]> exportUserListToExcel(@RequestBody SearchUserRequest searchUserRequest) {
        byte[] excelBytes = userQueryService.exportUserListToExcel(searchUserRequest);

        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=users.xlsx").body(excelBytes);
    }
}
