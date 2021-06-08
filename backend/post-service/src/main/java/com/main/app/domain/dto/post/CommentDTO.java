package com.main.app.domain.dto.post;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private String comment;
}
