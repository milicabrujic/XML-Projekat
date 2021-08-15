package com.main.app.service.post;

import com.main.app.domain.model.post.Favorite;

import java.util.List;

public interface FavoriteService {

    void favorite(Long postId, boolean favoriteValue);
    List<Favorite> getForUser();
}
