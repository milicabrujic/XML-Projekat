package com.main.app.repository.post;

import com.main.app.domain.model.post.Comment;
import com.main.app.domain.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long id);
}
