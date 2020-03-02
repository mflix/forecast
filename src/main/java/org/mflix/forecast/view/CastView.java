package org.mflix.forecast.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class CastView {
    private long id;
    private String name;

    public CastView() {
    }

    public CastView(String name) {
        this.name = name;
    }

    public CastView(long id, String name) {
        this.id = id;
        this.name = name;
    }
}