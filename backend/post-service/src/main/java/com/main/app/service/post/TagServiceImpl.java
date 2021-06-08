package com.main.app.service.post;

import com.main.app.domain.model.post.*;
import com.main.app.repository.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Override
    public Tag addTag(String name, Long postId) {

        Optional<Tag> dbTag = tagRepository.findOneByName(name);
        Tag tag = null;
        if (!dbTag.isPresent()) {
            tag = new Tag();
            tag.setName(name);
            tag = tagRepository.save(tag);
        }
        else {
            tag = dbTag.get();
        }

        Optional<Post> post = postRepository.findById(postId);

        if(!post.isPresent()) {
            return null;
        }

        PostTag postTag = new PostTag();
        postTag.setTag(tag);
        postTag.setPost(post.get());

        postTagRepository.save(postTag);

        return tag;
    }
}
