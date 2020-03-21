package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class CommentView {

    private long id;
    //电影
    private long movieId;
    //用户id
    private long userId;
    //用户的昵称
    private String userName;
    //用户的头像
    private String userImage;
    //评论内容

    private String content;
    //点赞数
    private long likenum;
    //是否删除 0是正常，1是删除
    private int isdelete;
    //评论时间
    private Date date;
    //本人是否点赞
    private boolean islike;


    public CommentView() {
    }


    public CommentView(long id, long userId, String userName, String userImage, long movieId, String content, long likenum, int isdelete, Date date, boolean islike) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.movieId = movieId;
        this.content = content;
        this.likenum = likenum;
        this.isdelete = isdelete;
        this.date = date;
        this.islike = islike;
    }

}