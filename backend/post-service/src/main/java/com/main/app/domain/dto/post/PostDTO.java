package com.main.app.domain.dto.post;

import com.main.app.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String name;
    private String description;
    private String content;

    private String tag;
    private String location;
    private Long albumId;
    private boolean higlight;
}