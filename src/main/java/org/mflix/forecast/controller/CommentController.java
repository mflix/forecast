package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.CommentService;
import org.mflix.forecast.service.MovieService;
import org.mflix.forecast.view.CommentView;
import org.mflix.forecast.view.MovieView;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final ResponseComponent responseComponent;
    private final CommentService commentService;

    @Autowired
    public CommentController(ResponseComponent responseComponent, CommentService commentService) {
        this.commentService = commentService;
        this.responseComponent = responseComponent;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseView> postByBody(@Valid @RequestBody CommentView commentView) {
        commentView = commentService.createByBody(commentView);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, commentView);
    }

    @GetMapping("/page/")
    public ResponseEntity<ResponseView> getAllWithPage(@RequestParam(required = true) long movieId, Pageable pageable) {
        var commentViewPage = commentService.readAllWithPage(movieId, pageable);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, commentViewPage);
    }

    //点赞
    @GetMapping("/likecomment/")
    public ResponseEntity<ResponseView> likeComment(long commentId, long userId) {
        commentService.likeComment(commentId, userId);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK);
    }


    //TODO 删除评论


}