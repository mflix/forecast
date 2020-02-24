package org.mflix.forecast.enumeration;

import lombok.Getter;

@Getter
public enum StatusEnumeration {
    S0(0, "SUCCESS");

    private int code;
    private String message;

    private StatusEnumeration(int code, String message) {
        this.code = code;
        this.message = message;
    }
}