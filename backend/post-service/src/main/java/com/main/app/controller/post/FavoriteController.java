package com.main.app.controller.post;

import com.main.app.domain.model.post.Favorite;
import com.main.app.service.post.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<Favorite>> get() {
        return new ResponseEntity<List<Favorite>>(favoriteService.getForUser(), HttpStatus.OK);
    }

    @PostMapping(path = "/favorite/{postId}")
    public void favorite(@PathVariable Long postId) {
        favoriteService.favorite(postId, true);
    }

    @PostMapping(path = "/unfavorite/{postId}")
    public void unfavorite(@PathVariable Long postId) {
        favoriteService.favorite(postId, false);
    }
}
