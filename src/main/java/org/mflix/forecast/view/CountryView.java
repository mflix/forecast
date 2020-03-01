package org.mflix.forecast.view;

import lombok.Data;

@Data
public class CountryView {
    private long id;
    private String name;

    public CountryView() {
    }

    public CountryView(String name) {
        this.name = name;
    }

    public CountryView(long id, String name) {
        this.id = id;
        this.name = name;
    }
}