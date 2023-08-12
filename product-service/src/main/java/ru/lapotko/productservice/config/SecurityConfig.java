package ru.lapotko.productservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import ru.lapotko.shared.security.jwt.KeyCloakJwtGrantedAuthoritiesConverter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    private final KeyCloakJwtGrantedAuthoritiesConverter converter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServerConfigurer -> resourceServerConfigurer
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }

    //TODO add custom outside docker issuerUri
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String dockerIssuerUri;
    @Profile("docker")
    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(dockerIssuerUri);
        jwtDecoder.setJwtValidator(JwtValidators.createDefault());
        return jwtDecoder;
    }
}
