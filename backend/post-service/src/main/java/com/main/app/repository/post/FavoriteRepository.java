package com.main.app.repository.post;

import com.main.app.domain.model.post.Comment;
import com.main.app.domain.model.post.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findOneByUserIdAndPostId(Long userId, Long postId);
    List<Favorite> findAllByUserId(Long userId);
}
