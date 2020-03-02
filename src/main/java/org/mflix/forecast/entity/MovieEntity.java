package org.mflix.forecast.entity;

import java.util.Date;

import javax.persistence.Column;
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
    // 海报链接
    private String imageUrl;
    // 原标题
    private String originalTitle;
    // 上映日期
    private Date pubdate;
    // 评分
    private Double rating;
    // 类型
    private String subtype;
    // 简介
    @Column(length = 2048)
    private String summary;
    // 标题
    private String title;

    public MovieEntity() {
    }

    public MovieEntity(String imageUrl, String originalTitle, Date pubdate, Double rating, String subtype,
            String summary, String title) {
        this.imageUrl = imageUrl;
        this.originalTitle = originalTitle;
        this.pubdate = pubdate;
        this.rating = rating;
        this.subtype = subtype;
        this.summary = summary;
        this.title = title;
    }
}