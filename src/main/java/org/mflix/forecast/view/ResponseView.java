package org.mflix.forecast.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
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