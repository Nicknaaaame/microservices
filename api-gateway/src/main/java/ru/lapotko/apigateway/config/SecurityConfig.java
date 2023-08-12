package ru.lapotko.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    //TODO add custom outside docker issuerUri
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String dockerIssuerUri;
    @Profile("docker")
    @Bean
    ReactiveJwtDecoder reactiveJwtDecoder() {
        NimbusReactiveJwtDecoder reactiveJwtDecoder = (NimbusReactiveJwtDecoder)
                ReactiveJwtDecoders.fromIssuerLocation(dockerIssuerUri);
        reactiveJwtDecoder.setJwtValidator(JwtValidators.createDefault());
        return reactiveJwtDecoder;
    }
}
