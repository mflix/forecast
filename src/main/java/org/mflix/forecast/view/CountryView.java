package org.mflix.forecast.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
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