package org.mflix.forecast.view;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
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