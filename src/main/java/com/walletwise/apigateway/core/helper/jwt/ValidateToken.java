package com.walletwise.apigateway.core.helper.jwt;

import com.walletwise.apigateway.core.helper.security.SingKey;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class ValidateToken {

    public void validate(final String token) {
        Jwts.parserBuilder().setSigningKey(SingKey.getSignKey()).build().parseClaimsJws(token);
    }
}
