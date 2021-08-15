package com.main.app.repository.post;

import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.post.PostLocation;
import com.main.app.domain.model.post.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

    List<PostTag> findAllByTagId(Long locationId);
}
