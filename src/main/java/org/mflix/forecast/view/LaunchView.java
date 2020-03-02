package org.mflix.forecast.view;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class LaunchView {
    private long id;
    private Date date;
    private String version;

    public LaunchView() {
    }

    public LaunchView(long id, Date date, String version) {
        this.id = id;
        this.date = date;
        this.version = version;
    }
}