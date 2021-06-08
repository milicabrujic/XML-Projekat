package com.main.app.converter.post;

import com.main.app.domain.dto.post.PostDTO;

import com.main.app.domain.model.post.Post;


import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post postDTOtoPostEntity(PostDTO postDTO){
        return Post
                .builder()
                .name(postDTO.getName())
                .description(postDTO.getDescription())
                .content(postDTO.getContent())
                .build();
    }

    public static PostDTO postEntityToPostDTO(Post post){
        return PostDTO
                .builder()
                .name(post.getName())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }

    public static List<PostDTO> postsListToPostsDTOList(List<Post> posts) {
        return posts
                .stream()
                .map(post -> postEntityToPostDTO(post))
                .collect(Collectors.toList());
    }
}
