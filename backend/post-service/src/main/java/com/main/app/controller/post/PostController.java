package com.main.app.controller.post;

import com.main.app.converter.post.PostConverter;
import com.main.app.domain.dto.post.PostDTO;
import com.main.app.enums.PostType;
import com.main.app.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/public/post")
    public ResponseEntity<List<PostDTO>> getPublicPosts() {
        return new ResponseEntity<>(PostConverter.postsListToPostsDTOList(postService.findAllByPostType(PostType.POST)), HttpStatus.OK);
    }

    @GetMapping(path = "/public/story")
    public ResponseEntity<List<PostDTO>> getPublicStories() {
        return new ResponseEntity<>(PostConverter.postsListToPostsDTOList(postService.findAllByPostType(PostType.STORY)), HttpStatus.OK);
    }

    @GetMapping(path = "/user/post/{id}")
    public ResponseEntity<List<PostDTO>> getPosts(@PathVariable Long id) {
        return new ResponseEntity<>(PostConverter.postsListToPostsDTOList(postService.findAllByUser(id, PostType.POST)), HttpStatus.OK);
    }

    @GetMapping(path = "/user/story/{id}")
    public ResponseEntity<List<PostDTO>> getStories(@PathVariable Long id) {
        return new ResponseEntity<>(PostConverter.postsListToPostsDTOList(postService.findAllByUser(id, PostType.STORY)), HttpStatus.OK);
    }

    @PostMapping(path = "/post")
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(PostConverter.postEntityToPostDTO(postService.add(postDTO, PostType.POST)), HttpStatus.OK);
    }

    @PostMapping(path = "/story")
    public ResponseEntity<PostDTO> addStory(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(PostConverter.postEntityToPostDTO(postService.add(postDTO, PostType.STORY)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
