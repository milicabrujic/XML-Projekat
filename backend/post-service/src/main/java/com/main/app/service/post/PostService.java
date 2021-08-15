package com.main.app.service.post;

import com.main.app.domain.dto.post.PostDTO;
import com.main.app.domain.model.post.Post;
import com.main.app.enums.PostType;

import java.util.List;

public interface PostService {

    Post add(PostDTO postDTO, PostType postType);
    List<Post> findAllByPostType(PostType postType, String search);
    List<Post> findAllByUser(Long id, PostType postType);
    Boolean deletePost(Long id);
    List<Post> getWithReaction();
}
