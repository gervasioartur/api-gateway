package com.walletwise.apigateway.filter;

import com.walletwise.apigateway.service.contract.IGetLoggedInUserEmailService;
import com.walletwise.apigateway.service.contract.IValidateTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator validator;
    @Autowired
    private IValidateTokenService service;
    @Autowired
    private IGetLoggedInUserEmailService getLoggedInUserEmailService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (!validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                }

                try {
                    service.validate(token);
                    String loggedInUserEmail = this.getLoggedInUserEmailService.get(token);
                    request = exchange.getRequest()
                            .mutate()
                            .header("loggedInUserEmail", loggedInUserEmail)
                            .build();

                } catch (Exception e) {
                    System.out.println("invalid access...!");
                    throw new RuntimeException("un authorized access to application");
                }
            }
            if (request == null)
                return chain.filter(exchange);
            else
                return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config {

    }
}
