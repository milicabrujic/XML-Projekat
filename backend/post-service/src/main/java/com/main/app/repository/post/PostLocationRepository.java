package com.main.app.repository.post;

import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.post.PostLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLocationRepository extends JpaRepository<PostLocation, Long> {
}
