package ru.lapotko.shared.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class SharedSecurityConfig {
    //TODO add custom outside docker issuerUri
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String dockerIssuerUri;

    @Profile("docker")
    @Bean
    JwtDecoder reactiveJwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(dockerIssuerUri);
        jwtDecoder.setJwtValidator(JwtValidators.createDefault());
        return jwtDecoder;
    }
}
