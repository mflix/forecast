package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import lombok.Data;

@Data
public class MovieView {
    private long id;
    private String name;
    private String postersUrl;
    private Date releaseDate;
    private String type;
    private String Introduction;
    private Set<String> tags;
    private String director;
    private String starring;
    private Set<Date> launchDate;
    private String score;
}