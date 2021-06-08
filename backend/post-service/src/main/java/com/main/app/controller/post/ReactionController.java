package com.main.app.controller.post;

import com.main.app.enums.ReactionType;
import com.main.app.service.post.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping(path = "/like/{postId}")
    public void like(@PathVariable Long id) {
        reactionService.add(id, ReactionType.LIKE);
    }

    @PostMapping(path = "/dislike/{postId}")
    public void dislike(@PathVariable Long id) {
        reactionService.add(id, ReactionType.DISLIKE);
    }
}
