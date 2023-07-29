package ru.lapotko.shared.security.jwt;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class KeyCloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Collection<GrantedAuthority> grantedAuthorities = defaultConverter.convert(source);
        if (source.getClaim("realm_access") == null) {
            return grantedAuthorities;
        }
        JSONObject realmAccess = new JSONObject(source.getClaim("realm_access"));
        if (realmAccess.get("roles") == null) {
            return grantedAuthorities;
        }

        JSONArray roles = new JSONArray();
        roles.addAll((ArrayList<Object>) realmAccess.get("roles"));

        final List<SimpleGrantedAuthority> keycloakAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
        grantedAuthorities.addAll(keycloakAuthorities);

        return grantedAuthorities;
    }
}
