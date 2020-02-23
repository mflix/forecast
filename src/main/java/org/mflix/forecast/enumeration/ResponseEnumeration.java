package org.mflix.forecast.enumeration;

import lombok.Getter;

@Getter
public enum ResponseEnumeration {
    S0(0, "SUCCESS");

    private int code;
    private String message;

    private ResponseEnumeration(int code, String message) {
        this.code = code;
        this.message = message;
    }
}