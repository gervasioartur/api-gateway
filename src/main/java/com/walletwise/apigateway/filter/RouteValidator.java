package com.walletwise.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/v1/api/auth/swagger-ui/.*",
            "/v1/api/auth/v3/.*",
            "/v1/api/auth/token/validate",
            "/v1/api/auth/token/validate/.*",
            "/v1/api/auth/register",
            "/v1/api/auth/register/.*",
            "/v1/api/auth/login",
            "/v1/api/auth/login/.*",
            "/v1/api/auth/validateCode",
            "/v1/api/auth/validateCode/.*",
            "/v1/api/auth/recoveryPassword",
            "/v1/api/auth/recoveryPassword/.*",
            "/v1/api/auth/newPassword",
            "/v1/api/auth/newPassword/.*"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .anyMatch(uri -> Pattern.matches(uri, request.getURI().getPath()));
}