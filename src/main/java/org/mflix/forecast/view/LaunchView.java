package org.mflix.forecast.view;

import java.util.Date;

import lombok.Data;

@Data
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