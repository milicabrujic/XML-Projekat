package com.main.app.repository.post;

import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.post.PostLocation;
import com.main.app.enums.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLocationRepository extends JpaRepository<PostLocation, Long> {

    List<PostLocation> findAllByLocationId(Long locationId);
}
