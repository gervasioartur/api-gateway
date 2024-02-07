package com.walletwise.apigateway.service.impl;

import com.walletwise.apigateway.core.helper.security.SingKey;
import com.walletwise.apigateway.service.contract.IGetLoggedInUserEmailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GetLoggedInUserEmailService implements IGetLoggedInUserEmailService {
    @Override
    public String get(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(SingKey.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
