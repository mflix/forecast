package org.mflix.forecast.repository;

import java.util.Set;

import java.util.Optional;

import org.mflix.forecast.entity.LikeCommentEntity;
import org.mflix.forecast.entity.MovieGenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeCommentEntity, Long> {
    LikeCommentEntity findByCommentIdAndUserId(long commentId, long userId);

}