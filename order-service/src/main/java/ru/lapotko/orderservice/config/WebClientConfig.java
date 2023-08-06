package ru.lapotko.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.lapotko.shared.security.jwt.JwtUtil;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter(setAuthorization());
    }

    private ExchangeFilterFunction setAuthorization() {
        return ExchangeFilterFunction.ofRequestProcessor((clientRequest) -> {
            ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                    .header("AUTHORIZATION", JwtUtil.getAuthorizationHeader())
                    .build();
            return Mono.just(authorizedRequest);
        });
    }
}
