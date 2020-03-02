package org.mflix.forecast.enumeration;

import lombok.Getter;

@Getter
public enum RoleEnumeration {
    USER("ROLE_USER"), UPLOADER("ROLE_UPLOADER"), ADMIN("ROLE_ADMIN");

    private String name;

    private RoleEnumeration(String name) {
        this.name = name;
    }
}