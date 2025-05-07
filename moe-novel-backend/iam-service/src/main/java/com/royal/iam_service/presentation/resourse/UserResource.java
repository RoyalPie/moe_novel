package com.royal.iam_service.presentation.resourse;

import com.evo.common.UserAuthority;
import com.evo.common.dto.response.Response;
import com.royal.iam_service.application.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserResource {
    private final UserQueryService userQueryService;
//    @GetMapping("/{userId}/authority")
//    public Response<UserAuthority> getUserAuthority(@PathVariable UUID userId) {
//
//    }

    @GetMapping("/{username}/authorities-by-username")
    public Response<UserAuthority> getUserAuthorityByUsername(@PathVariable String username) {
        UserAuthority userAuthority = userQueryService.getUserAuthority(username);
        Response<UserAuthority> response = new Response<UserAuthority>().success();
        response.setData(userAuthority);
        return response;
    }

    @PostMapping("/revoke-refresh-token")
    public void revokeRefreshToken(@RequestBody String refreshToken) {

    }
}
