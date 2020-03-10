package org.mflix.forecast.enumeration;

import lombok.Getter;

@Getter
public enum StatusEnumeration {
    S0(0, "SUCCESS"), F1(1, "NOT SUCH OBJECT"), F2(2, "METHOD OR ARGUMENT NOT VALID"),
    F3(3, "HTTP REQUEST METHOD NOT SUPPORTED"),E1(4,"ERROR");

    private int code;
    private String message;

    private StatusEnumeration(int code, String message) {
        this.code = code;
        this.message = message;
    }
}