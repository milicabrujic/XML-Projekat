package com.main.app.service.post;

import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.post.Reaction;
import com.main.app.enums.ReactionType;
import com.main.app.repository.post.PostRepository;
import com.main.app.repository.post.ReactionRepository;
import com.main.app.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public List<Reaction> get(Long postId, ReactionType reactionType) {
        return reactionRepository.findAllByPostIdAndReactionType(postId, reactionType);
    }

    @Override
    public Reaction add(Long postId, ReactionType reactionType) {
        Optional<Post> post = postRepository.findById(postId);

        if(!post.isPresent()) {
            return null;
        }

        Reaction reaction = new Reaction();
        reaction.setPost(post.get());
        reaction.setUser(currentUserService.getCurrentUser().get());
        reaction.setReactionType(reactionType);

        return reactionRepository.save(reaction);
    }
}
