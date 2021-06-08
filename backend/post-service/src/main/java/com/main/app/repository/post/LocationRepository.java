package com.main.app.repository.post;

import com.main.app.domain.model.post.Location;
import com.main.app.domain.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findOneByName(String name);
}
