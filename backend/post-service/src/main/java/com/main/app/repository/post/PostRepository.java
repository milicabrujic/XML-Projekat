package com.main.app.repository.post;

import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.user.User;
import com.main.app.enums.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByPublicPostTrueAndPostType(PostType type);
    List<Post> findAllByUserIdAndPostType(Long userId, PostType type);
}
