package org.mflix.forecast.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class TestView {
    private long id;
    private String text;

    public TestView(long id, String text) {
        this.id = id;
        this.text = text;
    }
}