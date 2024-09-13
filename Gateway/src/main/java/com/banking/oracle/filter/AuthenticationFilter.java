package com.banking.oracle.filter;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private WebClient.Builder webClientBuilder;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			if (validator.isSecured.test(exchange.getRequest())) {
				// Get the token from the request header
				String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

				if (token == null || !token.startsWith("Bearer ")) {
					exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
					return exchange.getResponse().setComplete();
				}

				// Extract the token part after "Bearer "
				String actualToken = token.substring(7);				
				webClientBuilder.build()
						.get()
						.uri("lb://AUTHENTICATION-SERVICE/auth/validate?token=" + actualToken)
						.retrieve()
						.onStatus(status -> status.isError(), clientResponse -> {
							System.out.println("Error status code: " + clientResponse.statusCode());
							return Mono.error(new RuntimeException("Invalid token"));
						})
						.bodyToMono(String.class)
						.timeout(Duration.ofSeconds(10))
						.flatMap(response -> {
							System.out.println("Token verified successfully");
							return chain.filter(exchange);
						})
						.onErrorResume(e -> {
							e.printStackTrace();
							exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
							return exchange.getResponse().setComplete();
						});
			}

			return chain.filter(exchange);
		});
	}

	public static class Config {
	}
}