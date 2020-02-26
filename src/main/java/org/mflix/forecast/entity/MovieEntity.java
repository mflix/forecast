package org.mflix.forecast.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class MovieEntity {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String postersUrl;
    private Date releaseDate;
    private String type;
    private String Introduction;
    @ElementCollection
    private Set<String> tags;
    private String director;
    private String starring;
    @ElementCollection
    private Set<Date> launchDate;
    private String score;
}