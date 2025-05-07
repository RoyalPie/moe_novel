package com.evo.common.webapp.security.impl;

import com.evo.common.client.iam.IamClient;
import com.evo.common.webapp.security.TokenCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.evo.common.webapp.support.SecurityUtils.getJwtExpiry;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenCacheServiceImpl implements TokenCacheService {
    private final IamClient iamClient;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void invalidToken(String token) {
        long expiration = getJwtExpiry(token) - System.currentTimeMillis();
        redisTemplate.opsForValue().set(token, INVALID_TOKEN_CACHE, expiration, TimeUnit.MILLISECONDS);
    }

    @Override
    public void invalidRefreshToken(String refreshToken) {
        iamClient.invalidRefreshToken(refreshToken);
    }

    @Override
    public boolean isExisted(String cacheName, String token) {
        if (Objects.equals(cacheName, INVALID_TOKEN_CACHE)) return redisTemplate.hasKey(token);
        else return true;
    }

}
