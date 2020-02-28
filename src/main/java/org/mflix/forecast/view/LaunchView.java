package org.mflix.forecast.view;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class LaunchView {
    private long id;
    private String type;
    private Date date;

    public LaunchView() {
    }

    public LaunchView(long id, String type, Date date) {
        this.id = id;
        this.type = type;
        this.date = date;
    }
}