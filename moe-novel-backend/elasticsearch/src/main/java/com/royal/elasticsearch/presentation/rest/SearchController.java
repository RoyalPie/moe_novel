package com.royal.elasticsearch.presentation.rest;

import com.evo.common.dto.response.PageApiResponse;
import com.royal.elasticsearch.application.dto.request.SearchNovelRequest;
import com.royal.elasticsearch.application.dto.request.SearchUserRequest;
import com.royal.elasticsearch.application.dto.response.SearchNovelResponse;
import com.royal.elasticsearch.application.dto.response.SearchUserResponse;
import com.royal.elasticsearch.application.service.NovelQueryService;
import com.royal.elasticsearch.application.service.UserQueryService;
import com.royal.elasticsearch.domain.Novel;
import com.royal.elasticsearch.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/")
public class SearchController {
    private final UserQueryService userQueryService;
    private final NovelQueryService novelQueryService;

    @PreAuthorize("hasPermission('user', 'admin')")
    @GetMapping("/users")
    PageApiResponse<List<User>> searchUser(@RequestBody SearchUserRequest request) {
        SearchUserResponse searchUserResponse = userQueryService.searchUser(request);
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageIndex(searchUserResponse.getPageIndex())
                .totalPages(searchUserResponse.getTotalPages())
                .totalElements(searchUserResponse.getTotalElements())
                .pageSize(searchUserResponse.getPageSize())
                .hasPrevious(searchUserResponse.isHasPrevious())
                .hasNext(searchUserResponse.isHasNext())
                .build();

        return PageApiResponse.<List<User>>builder()
                .data(searchUserResponse.getUsers())
                .pageable(pageableResponse)
                .success(true)
                .code(200)
                .message("Search user successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();

    }
    @PreAuthorize("hasPermission('user', 'admin')")
    @GetMapping("/novels")
    PageApiResponse<List<Novel>> searchNovel(@RequestBody SearchNovelRequest request) {
        SearchNovelResponse searchNovelResponse = novelQueryService.searchNovel(request);
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageIndex(searchNovelResponse.getPageIndex())
                .totalPages(searchNovelResponse.getTotalPages())
                .totalElements(searchNovelResponse.getTotalElements())
                .pageSize(searchNovelResponse.getPageSize())
                .hasPrevious(searchNovelResponse.isHasPrevious())
                .hasNext(searchNovelResponse.isHasNext())
                .build();

        return PageApiResponse.<List<Novel>>builder()
                .data(searchNovelResponse.getNovels())
                .pageable(pageableResponse)
                .success(true)
                .code(200)
                .message("Search novel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();

    }
}
