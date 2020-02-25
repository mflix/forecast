package org.mflix.forecast.enumeration;

import lombok.Getter;

@Getter
public enum RoleEnumeration {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), GUEST("ROLE_GUEST");

    private String name;

    private RoleEnumeration(String name) {
        this.name = name;
    }
}