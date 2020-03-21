package org.mflix.forecast.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue
    private long id;
    //电影
    private long movieId;
    //用户id
    private long userId;
    //评论内容
    @Column(length = 2048)
    private String content;
    //点赞数
    private long likenum;
    //是否删除 0是正常，1是删除
    private int isdelete;
    //评论时间
    private Date date;

    public CommentEntity() {
    }

    public CommentEntity(long movieId, long userId, String content, long likenum, int isdelete, Date date) {
        this.movieId = movieId;
        this.userId = userId;
        this.content = content;
        this.likenum = likenum;
        this.isdelete = isdelete;
        this.date = date;
    }


}