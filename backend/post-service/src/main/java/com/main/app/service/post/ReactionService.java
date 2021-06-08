package com.main.app.service.post;

import com.main.app.domain.model.post.Reaction;
import com.main.app.enums.ReactionType;

import java.util.List;

public interface ReactionService {

    List<Reaction> get(Long postId, ReactionType reactionType);
    Reaction add(Long postId, ReactionType reactionType);
}
