package org.mflix.forecast.view;

import lombok.Data;

@Data
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