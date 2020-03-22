package org.mflix.forecast.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.component.DoubanComponent;
import org.mflix.forecast.entity.*;
import org.mflix.forecast.repository.*;
import org.mflix.forecast.view.CommentView;
import org.mflix.forecast.view.LaunchView;
import org.mflix.forecast.view.MovieView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, LikeCommentRepository likeCommentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.likeCommentRepository = likeCommentRepository;
        this.userRepository = userRepository;

    }

    //新增评论
    public CommentView createByBody(CommentView commentView) {

        CommentEntity commentEntity = new CommentEntity(commentView.getMovieId(), commentView.getUserId(), commentView.getContent(),
                commentView.getLikenum(), commentView.getIsdelete(), commentView.getDate() /*commentView.getIslike()默认是0*/);
        commentRepository.save(commentEntity);

        return commentView;
    }

    //评论分页查询
    public Page<CommentView> readAllWithPage(long movieId, long userId, Pageable pageable) {
        var commentEntityPage = commentRepository.findAllByMovieId(movieId, pageable);

        var commentViewList = transform(commentEntityPage.toList(), userId);
        return new PageImpl<>(commentViewList, pageable, commentViewList.size());
    }


    private List<CommentView> transform(List<CommentEntity> commentEntityList, long userId) {
        return commentEntityList.stream().map((commentEntity) -> {
            return transform(commentEntity, userId);
        }).collect(Collectors.toList());
    }


    //TODO 删除评论

    //点赞
    public void likeComment(long commentId, long userid) {
        //先判断是否点赞
        var likeEntity = likeCommentRepository.findByCommentIdAndUserId(commentId, userid);
        var commentEntity = commentRepository.findById(commentId);
        if (likeEntity != null && likeEntity.isIslike()) {
            //取消点赞，同时给该评论的点赞数-1

            likeCommentRepository.delete(likeEntity);
            commentEntity.setLikenum(commentEntity.getLikenum() - 1);
            commentRepository.save(commentEntity);

        } else {
            //进行点赞，同时给该评论的点赞数+1
            likeCommentRepository.save(new LikeCommentEntity(commentId, userid, true));
            commentEntity.setLikenum(commentEntity.getLikenum() + 1);
            commentRepository.save(commentEntity);
        }

    }


    private CommentView transform(CommentEntity commentEntity, long userId) {
        var commentEntityUserId = commentEntity.getUserId();
        var userEntity = userRepository.findById(commentEntityUserId).orElseThrow();
        var likeEntity = likeCommentRepository.findByCommentIdAndUserId(commentEntity.getId(), userId);
        boolean like = (likeEntity != null && likeEntity.isIslike());


        return new CommentView(commentEntity.getId(), userId, userEntity.getNickname(), userEntity.getAvatarUrl(),
                commentEntity.getMovieId(), commentEntity.getContent(), commentEntity.getLikenum(), commentEntity.getIsdelete(),
                commentEntity.getDate(), like);
    }


}