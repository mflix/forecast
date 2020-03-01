package org.mflix.forecast.view;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TestView {
    private long id;
    @NotBlank
    private String text;
    private String fileName;

    public TestView() {
    }

    public TestView(long id, String text) {
        this.id = id;
        this.text = text;
    }
}