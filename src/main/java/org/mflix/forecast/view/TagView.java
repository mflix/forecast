package org.mflix.forecast.view;

import lombok.Data;

@Data
public class TagView {
    private long id;
    private String name;

    public TagView() {
    }

    public TagView(String name) {
        this.name = name;
    }

    public TagView(long id, String name) {
        this.id = id;
        this.name = name;
    }
}