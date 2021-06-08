package com.main.app.service.post;

import com.main.app.domain.model.post.Comment;
import com.main.app.domain.model.post.Post;
import com.main.app.repository.post.CommentRepository;
import com.main.app.repository.post.PostRepository;
import com.main.app.repository.post.TagRepository;
import com.main.app.repository.user.UserRepository;
import com.main.app.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public Comment addComment(Long postId, String comment) {

        Optional<Post> post = postRepository.findById(postId);

        if(!post.isPresent()) {
            return null;
        }

        Comment com = new Comment();
        com.setComment(comment);
        com.setUser(currentUserService.getCurrentUser().get());
        com.setPost(post.get());

        return commentRepository.save(com);
    }

    @Override
    public List<Comment> get(Long id) {
        return commentRepository.findAllByPostId(id);
    }
}
