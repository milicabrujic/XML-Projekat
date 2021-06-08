package com.main.app.repository.post;

import com.main.app.domain.model.post.Location;
import com.main.app.domain.model.post.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findOneByName(String name);
}
