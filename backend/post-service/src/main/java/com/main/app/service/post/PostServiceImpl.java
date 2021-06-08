package com.main.app.service.post;

import com.main.app.converter.post.PostConverter;
import com.main.app.converter.user.UserConverter;
import com.main.app.domain.dto.post.PostDTO;
import com.main.app.domain.model.post.Post;
import com.main.app.domain.model.user.User;
import com.main.app.enums.PostType;
import com.main.app.repository.post.PostRepository;
import com.main.app.service.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public Post add(PostDTO postDTO, PostType postType) {

        Optional<User> user = currentUserService.getCurrentUser();

        if(!user.isPresent()) {
            return null;
        }

        Post post = PostConverter.postDTOtoPostEntity(postDTO);
        post.setPostType(postType);
        post.setUser(user.get());

        return postRepository.save(post);
    }

    @Override
    public List<Post> findAllByPostType(PostType postType) {
        return postRepository.findAllByPublicPostTrueAndPostType(postType);
    }

    @Override
    public List<Post> findAllByUser(Long id, PostType postType) {
        return postRepository.findAllByUserIdAndPostType(id, postType);
    }

    @Override
    public Boolean deletePost(Long id) {

        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent()) {
            return false;
        }

        Post postToDelete = post.get();
        postToDelete.setDeleted(true);

        postRepository.save(postToDelete);
        return true;
    }
}
