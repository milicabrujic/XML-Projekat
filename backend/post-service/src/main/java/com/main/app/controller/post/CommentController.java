package com.main.app.controller.post;

import com.main.app.converter.post.CommentConverter;
import com.main.app.domain.dto.post.CommentDTO;
import com.main.app.service.post.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

    private final CommentService commentService;

    @GetMapping(path = "/{postId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long postId) {
        return new ResponseEntity<>(CommentConverter.commentsListToCommentsDTOList(commentService.get(postId)), HttpStatus.OK);
    }

    @PostMapping(path = "/{postId}")
    public ResponseEntity<CommentDTO> add(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(CommentConverter.commentEntityToCommentDTO(commentService.addComment(postId, commentDTO.getComment())), HttpStatus.OK);
    }
}