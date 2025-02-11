package com.croydon.security;

import com.croydon.exceptions.UnauthorizedException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Jose
 */
@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    @Value("${jwt.convert.principal-atribute}")
    private String principalAtribute;
    @Value("${jwt.convert.resource.id}")
    private String resourceId;

    /**
     * Convierte un token JWT en una instancia de JwtAuthenticationToken.
     *
     * - Extrae las autoridades (roles) del token JWT. - Obtiene el nombre
     * principal del usuario autenticado. - Crea una instancia de
     * JwtAuthenticationToken con la información procesada.
     *
     * @param jwt Token JWT a convertir.
     * @return JwtAuthenticationToken con los roles y el nombre principal del
     * usuario.
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Collection<GrantedAuthority> authorities = Stream
                .concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extracResourceRole(jwt).stream())
                .toList();
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalName(jwt));
    }

    /**
     * Extrae los roles del usuario desde el claim "resource_access" del JWT.
     *
     * - Verifica si el token contiene información sobre "resource_access". -
     * Obtiene los roles asignados al usuario dentro del recurso configurado. -
     * Convierte los roles en una lista de GrantedAuthority.
     *
     * @param jwt Token JWT del cual se extraerán los roles.
     * @return Colección de GrantedAuthority con los roles extraídos.
     */
    private Collection<? extends GrantedAuthority> extracResourceRole(Jwt jwt) {

        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (jwt.getClaim("resource_access") == null) {
            return List.of();
        }
        resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(resourceId) == null) {
            return List.of();
        }

        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        if (resource.get("roles") == null) {
            return List.of();
        }

        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role)))
                .toList();
    }

    /**
     * Obtiene el nombre principal del usuario desde el JWT.
     *
     * - Usa el atributo configurado en "jwt.convert.principal-atribute" si está
     * definido. - Por defecto, utiliza el claim "sub" (sujeto) del token.
     *
     * @param jwt Token JWT del cual se obtendrá el nombre principal.
     * @return Nombre principal del usuario autenticado.
     */
    private String getPrincipalName(Jwt jwt) {

        String claimName = JwtClaimNames.SUB;

        if (principalAtribute != null) {
            claimName = principalAtribute;
        }

        return jwt.getClaim(claimName);
    }

}
