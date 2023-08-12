package ru.lapotko.apigateway.config;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lapotko.shared.security.jwt.KeyCloakJwtGrantedAuthoritiesConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final KeyCloakJwtGrantedAuthoritiesConverter converter;

    public SecurityConfig(KeyCloakJwtGrantedAuthoritiesConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange ->
                        exchange.pathMatchers("/eureka/**")
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtSpec ->
                        jwtSpec.jwtAuthenticationConverter(converter())))
                .build();
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> converter() {
        return new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {

            @Override
            public Mono<AbstractAuthenticationToken> convert(Jwt source) {
                final JwtGrantedAuthoritiesConverter defaultConverter1 = new JwtGrantedAuthoritiesConverter();
                ReactiveJwtGrantedAuthoritiesConverterAdapter defaultConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(defaultConverter1);

                Collection<GrantedAuthority> grantedAuthorities = defaultConverter.convert(source).buffer().blockFirst();

                extracted(source, grantedAuthorities);
                return Mono.just(grantedAuthorities)
                        .map((authorities) -> new JwtAuthenticationToken(source, authorities));
            }
        };
    }

    private void extracted(Jwt source, Collection<GrantedAuthority> grantedAuthorities) {
        JSONObject realmAccess = new JSONObject(source.getClaim("realm_access"));
        if (source.getClaim("realm_access") == null) {
            return;
        }
        if (realmAccess.get("roles") == null) {
            return;
        }
        JSONArray roles = new JSONArray();
        roles.addAll((ArrayList<Object>) realmAccess.get("roles"));

        final List<SimpleGrantedAuthority> keycloakAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
        grantedAuthorities.addAll(keycloakAuthorities);
    }
}
