package com.main.app.service.post;

import com.main.app.domain.model.post.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Long postId, String comment);
    List<Comment> get(Long id);
}
