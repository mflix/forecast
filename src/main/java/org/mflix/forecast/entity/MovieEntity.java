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
    // 中文名称
    private String chineseName;
    // 简介
    @Column(length = 1023)
    private String introduction;
    // 英文名称
    private String originName;
    // 海报链接
    private String posterUrl;
    // 上映日期
    private Date releaseDate;
    // 评分
    private Double score;
    // 类型
    private String type;

    public MovieEntity() {
    }

    public MovieEntity(String chineseName, String introduction, String originName, String posterUrl, Date releaseDate,
            Double score, String type) {
        this.chineseName = chineseName;
        this.introduction = introduction;
        this.originName = originName;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.score = score;
        this.type = type;
    }
}