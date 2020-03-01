package org.mflix.forecast.view;

import lombok.Data;

@Data
public class DirectorView {
    private long id;
    private String name;

    public DirectorView() {
    }

    public DirectorView(String name) {
        this.name = name;
    }

    public DirectorView(long id, String name) {
        this.id = id;
        this.name = name;
    }
}