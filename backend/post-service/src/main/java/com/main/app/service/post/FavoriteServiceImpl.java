package com.main.app.service.post;

import com.main.app.domain.model.post.Favorite;
import com.main.app.domain.model.user.User;
import com.main.app.repository.post.FavoriteRepository;
import com.main.app.repository.post.PostRepository;
import com.main.app.service.user.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void favorite(Long postId, boolean favoriteValue) {

        User user = currentUserService.getCurrentUser();

        Optional<Favorite> favoriteOptional = favoriteRepository.findOneByUserIdAndPostId(user.getId(), postId);

        Favorite favorite = null;

        if(favoriteOptional.isPresent()) {
            favorite = favoriteOptional.get();
        }
        else {
            favorite = new Favorite();
        }

        favorite.setUserId(user.getId());
        favorite.setPost(postRepository.getOne(postId));
        favorite.setFavorite(favoriteValue);

        favoriteRepository.save(favorite);
    }

    @Override
    public List<Favorite> getForUser() {

        User user = currentUserService.getCurrentUser();

        return favoriteRepository.findAllByUserId(user.getId());
    }
}
