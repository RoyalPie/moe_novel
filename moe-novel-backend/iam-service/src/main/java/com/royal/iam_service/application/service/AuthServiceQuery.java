package com.royal.iam_service.application.service;

import org.springframework.stereotype.Service;

@Service
public interface AuthServiceQuery {
    String getClientToken(String clientId, String clientSecret);
}
