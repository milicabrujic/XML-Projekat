package com.main.app.converter.post;

import com.main.app.domain.dto.post.CommentDTO;
import com.main.app.domain.model.post.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static Comment commentDTOtoCommentEntity(CommentDTO commentDTO){
        return Comment
                .builder()
                .comment(commentDTO.getComment())
                .build();
    }

    public static CommentDTO commentEntityToCommentDTO(Comment comment){
        return CommentDTO
                .builder()
                .comment(comment.getComment())
                .build();
    }

    public static List<CommentDTO> commentsListToCommentsDTOList(List<Comment> comments) {
        return comments
                .stream()
                .map(album -> commentEntityToCommentDTO(album))
                .collect(Collectors.toList());
    }
}
