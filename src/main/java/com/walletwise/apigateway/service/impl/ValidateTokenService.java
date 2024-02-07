package com.walletwise.apigateway.service.impl;

import com.walletwise.apigateway.core.helper.jwt.ValidateToken;
import com.walletwise.apigateway.service.contract.IValidateTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateTokenService implements IValidateTokenService {
    private final ValidateToken validateToken;

    @Override
    public void validate(String token) {
        this.validateToken.validate(token);
    }
}
