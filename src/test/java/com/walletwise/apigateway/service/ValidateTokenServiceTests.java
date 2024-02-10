package com.walletwise.apigateway.service;

import com.walletwise.apigateway.core.helper.jwt.ValidateToken;
import com.walletwise.apigateway.service.contract.IValidateTokenService;
import com.walletwise.apigateway.service.impl.ValidateTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ValidateTokenServiceTests {
    @MockBean
    private IValidateTokenService service;
    @MockBean
    private ValidateToken validateToken;

    @BeforeEach
    public void setup(){
        this.service = new ValidateTokenService(validateToken);
    }

    @Test
    @DisplayName("should call validateToken with correct params")
    public void shouldCallValidateTokenWithCorrectParams(){
        String token = UUID.randomUUID().toString();
        this.service.validate(token);
        verify(validateToken, atLeast(1)).validate(token);
    }
}
