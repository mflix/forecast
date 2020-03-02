package org.mflix.forecast.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class GenreView {
    private long id;
    private String name;

    public GenreView() {
    }

    public GenreView(String name) {
        this.name = name;
    }

    public GenreView(long id, String name) {
        this.id = id;
        this.name = name;
    }
}