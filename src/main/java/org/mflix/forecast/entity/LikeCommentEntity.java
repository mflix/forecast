package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class LikeCommentEntity {
    @Id
    @GeneratedValue
    private long id;
    //评论id
    private long commentId;
    //用户id
    private long userId;
    //是否点赞
    private boolean islike;


    public LikeCommentEntity() {
    }

    public LikeCommentEntity(long commentId, long userId, boolean islike) {
        this.commentId = commentId;
        this.userId = userId;
        this.islike = islike;

    }


}