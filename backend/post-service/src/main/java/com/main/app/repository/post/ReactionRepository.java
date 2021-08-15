package com.main.app.repository.post;

import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.post.Reaction;
import com.main.app.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<Reaction> findAllByPostIdAndReactionType(Long id, ReactionType reactionType);

    List<Reaction> findAllByUserId(Long userId);
}
