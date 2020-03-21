package org.mflix.forecast.repository;

import java.util.Set;

import org.mflix.forecast.entity.CommentEntity;
import org.mflix.forecast.entity.LaunchEntity;
import org.mflix.forecast.entity.LikeCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    //根据电影的id进行分页查询
    Page<CommentEntity> findAllByMovieId(long movieId, Pageable pageable);

    //根据评论id获取评论对象
    CommentEntity findById(long commentId);

}