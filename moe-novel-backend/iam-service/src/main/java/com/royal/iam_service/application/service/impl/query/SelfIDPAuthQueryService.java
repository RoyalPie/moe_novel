package com.royal.iam_service.application.service.impl.query;

import com.royal.iam_service.application.config.TokenProvider;
import com.royal.iam_service.application.service.AuthServiceQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("self_idp_auth_query_service")
@RequiredArgsConstructor
@Slf4j
public class SelfIDPAuthQueryService implements AuthServiceQuery {
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String getClientToken(String clientId, String clientSecret) {
        return null;
    }
}
