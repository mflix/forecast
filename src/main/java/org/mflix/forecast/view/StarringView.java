package org.mflix.forecast.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class StarringView {
    private long id;
    private String name;

    public StarringView() {
    }

    public StarringView(String name) {
        this.name = name;
    }

    public StarringView(long id, String name) {
        this.id = id;
        this.name = name;
    }
}