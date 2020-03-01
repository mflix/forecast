package org.mflix.forecast.view;

import lombok.Data;

@Data
public class ResponseView {
    private int code;
    private String message;
    private String cause;
    private Object object;

    public ResponseView(int code, String message) {
        this.code = code;
        this.message = message;
    }
}