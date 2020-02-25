package org.mflix.forecast.component;

import java.util.Set;
import java.util.stream.Collectors;

import org.mflix.forecast.enumeration.RoleEnumeration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleComponent {
    public Set<GrantedAuthority> generate(RoleEnumeration... roleEnumerations) {
        return Set.of(roleEnumerations).stream().map((roleEnumeration) -> {
            return new SimpleGrantedAuthority(roleEnumeration.getName());
        }).collect(Collectors.toSet());
    }
}